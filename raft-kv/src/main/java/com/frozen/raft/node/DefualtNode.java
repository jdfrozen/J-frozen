package com.frozen.raft.node;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.frozen.raft.config.NodeConfig;
import com.frozen.raft.current.RaftThreadPool;
import com.frozen.raft.db.ClientKVAck;
import com.frozen.raft.db.ClientKVReq;
import com.frozen.raft.db.LogDb;
import com.frozen.raft.raftlog.LogEntry;
import com.frozen.raft.rpc.Response;
import com.frozen.raft.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
@Slf4j
public class DefualtNode<T> implements Node<T>{
	@Autowired
	private NodeConfig nodeConfig;
	@Autowired
	private HttpUtils httpUtils;
	@Autowired
	private LogDb logDb;
	/** final */
	private volatile Peer self;
	/** 集群配置列表 */
	private List<Peer> list = new ArrayList<>();
	private volatile Peer leader;
	/** 服务器最后一次知道的任期号（初始化为 0，持续递增） */
	private volatile long currentTerm = 0;
	/** 在当前获得选票的候选人的 Id */
	volatile String votedFor;
	/** 节点当前状态 */
	private volatile int status = NodeStatus.FOLLOWER;
	/** 选举锁 */
	private volatile ReentrantLock electionLock = new ReentrantLock(false);
	/** 选举时间间隔基数 */
	private volatile long electionTime = 15 * 1000;
	/** 上一次选举时间 */
	private volatile long preElectionTime = 0;
	/** 上次一心跳时间戳 */
	private volatile long preHeartBeatTime = 0;
	/** 心跳间隔基数 */
	private final long heartBeatTick = 5 * 1000;
	/** 心跳锁 */
	private volatile ReentrantLock heartBeatLock = new ReentrantLock(false);

	@Override
	@PostConstruct
	public void init(){
		this.self=Peer.builder().addr("127.0.0.1").port(nodeConfig.getSelfPort()).build();
		for(String peerAddr: nodeConfig.getPeerAddrs()){
			this.list.add(Peer.getPeer(peerAddr));
		}
		//心跳
		RaftThreadPool.scheduleWithFixedDelay(new HeartbeatTask(this),500);
		//选举
		RaftThreadPool.scheduleAtFixedRate(new ElectionTask(this),5000,100);
	}

	@Override
	public void destroy() {}

	static class  HeartbeatTask implements Runnable {
		private DefualtNode defualtNode;
		private  List<Peer> list;
		public HeartbeatTask(DefualtNode defualtNode){
			this.defualtNode = defualtNode;
			this.list = defualtNode.list;
		}
		@Override
		public void run() {
			//选举锁，使用非阻塞锁
			if(!defualtNode.heartBeatLock.tryLock()){
				return;
			}
			try {
				log.info("HeartbeatTask start");
				String leaderStr = defualtNode.leader.getPeerStr();
				String selfStr = defualtNode.self.getPeerStr();
				AentryParam param = AentryParam.builder()
						.leaderId(leaderStr)
						.serverId(selfStr)
						.term(defualtNode.currentTerm)
						.build();
				if (!selfStr.equals(leaderStr)) {
					log.info("{} is not leader,leader is {}", selfStr, leaderStr);
					return;
				}
				for (Peer peer : list) {
					String url = "http://" + peer.getPeerStr() + "?";
					RaftThreadPool.execute(() -> {
						String json = defualtNode.httpUtils.sendMsg(url + "/heartbeat", JSON.toJSONString(param));
						Response<AentryResult> response = JSON.parseObject(json, new TypeReference<Response<AentryResult>>() {
						});
						if (response == null) {
							return;
						}
						AentryResult aentryResult = response.getResult();
						long term = aentryResult.getTerm();
						if (term > defualtNode.currentTerm) {
							log.error("self will become follower, he's term : {}, my term : {}", term, defualtNode.currentTerm);
							defualtNode.currentTerm = term;
							defualtNode.votedFor = "";
							defualtNode.status = NodeStatus.FOLLOWER;
						}
					});
				}
			}finally {
				defualtNode.heartBeatLock.unlock();
			}
		}
	}

	@Slf4j
	static class  ElectionTask implements Runnable {
		private DefualtNode defualtNode;
		private  List<Peer> list;
		public ElectionTask(DefualtNode defualtNode){
			this.defualtNode = defualtNode;
			this.list = defualtNode.list;
		}
		@Override
		public void run() {
			//选举锁，使用非阻塞锁
			if(!defualtNode.electionLock.tryLock()){
				return;
			}
			if(defualtNode.status==NodeStatus.LEADER){
				return;
			}
			try {
				if (defualtNode.status == NodeStatus.FOLLOWER) {
					//判断心跳时间是否过期
					if ((System.currentTimeMillis() - defualtNode.heartBeatTick) < defualtNode.preHeartBeatTime) {
						return;
					}
					defualtNode.status = NodeStatus.CANDIDATE;
					defualtNode.currentTerm++;
					defualtNode.preElectionTime = System.currentTimeMillis();
				}
				//判断选举时间是否过期
				if ((System.currentTimeMillis() - defualtNode.electionTime) < defualtNode.preElectionTime) {
					return;
				}
				log.info("start election ");
				//通过随机设置下一次选举错开选举
				defualtNode.preElectionTime = System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(150) + 150;
				//发送选举信息
				String selfStr = defualtNode.self.getPeerStr();
				defualtNode.votedFor = defualtNode.self.getPeerStr();
				int peerSize = list.size();
				CountDownLatch countDownLatch = new CountDownLatch(peerSize);
				List<RvoteResult> rvoteResults = Collections.synchronizedList(new ArrayList());
				for (Peer peer : list) {
					RvoteParam param = RvoteParam.builder()
							.serverId(selfStr)
							.candidateId(peer.getPeerStr())
							.term(defualtNode.currentTerm)
							.lastLogIndex(0)
							.lastLogTerm(0)
							.build();
					RaftThreadPool.execute(() -> {
						try {
							String url = "http://" + peer.getPeerStr() + "?";
							String json = defualtNode.httpUtils.sendMsg(url + "/requestVote", JSON.toJSONString(param));
							Response<RvoteResult> response = JSON.parseObject(json, new TypeReference<Response<RvoteResult>>() {
							});
							if (response != null) {
								RvoteResult rvoteResult = response.getResult();
								rvoteResults.add(rvoteResult);
							}
						} finally {
							countDownLatch.countDown();
						}
					});
				}
				try {
					countDownLatch.await(4000, MILLISECONDS);
					int count = 1;
					for (RvoteResult rvoteResult : rvoteResults) {
						long term = rvoteResult.getTerm();
						if (term > defualtNode.currentTerm) {
							defualtNode.currentTerm = term;
						} else {
							count++;
						}
					}
					// 如果投票期间,有其他合法的Leader——更新状态需要加锁
					if (defualtNode.status == NodeStatus.FOLLOWER) {
						return;
					}
					//得到大多数选票则成为leader
					if (count > (peerSize / 2)) {
						defualtNode.leader = defualtNode.self;
						defualtNode.status = NodeStatus.LEADER;
						defualtNode.votedFor = "";
					}else {
						//下次重新选举
						defualtNode.votedFor = "";
					}
				} catch (InterruptedException e) {
					log.error("", e);
				}
			}finally {
				defualtNode.electionLock.unlock();
			}
		}
	}

	@Override
	public RvoteResult handlerRequestVote(RvoteParam param) {
		/** 候选人的任期号  */
		long term =param.getTerm();
		if(this.currentTerm>term){
			return RvoteResult.builder().term(this.currentTerm).voteGranted(false).build();
		}
		LogEntry lastLogEntrieCommit = logDb.getlastLogEntrieCommit();
		/** 候选人最后日志条目的任期号  */
		long lastLogTerm=param.getLastLogTerm();
		if(lastLogTerm<lastLogEntrieCommit.getTerm()){
			return RvoteResult.builder().term(term).voteGranted(false).build();
		}
		/** 候选人的最后日志条目的索引值 */
		long lastLogIndex=param.getLastLogIndex();
		if(lastLogIndex<lastLogEntrieCommit.getIndex()){
			return RvoteResult.builder().term(term).voteGranted(false).build();
		}
		/** 请求选票的候选人的 Id(ip:selfPort) */
		String candidateId=param.getCandidateId();
		// 切换状态
		this.status = NodeStatus.FOLLOWER;
		// 更新
		this.leader=Peer.getPeer(candidateId);
		this.currentTerm = param.getTerm();
		this.votedFor=candidateId;
		return RvoteResult.builder().term(term).voteGranted(true).build();
	}

	/**
	 * 处理心跳
	 * @param param
	 * @return
	 */
	public AentryResult handlerHeartbeat(AentryParam param) {
		String myLeaderStr=leader.getPeerStr();
		String leaderStr=param.getLeaderId();
		//第一步，判断发心跳的是不是当前leader
		if(myLeaderStr.equals(leaderStr)){
			this.currentTerm = param.getTerm();
			this.preHeartBeatTime=System.currentTimeMillis();
			return AentryResult.builder().term(param.getTerm()).success(true).build();
		}else {
			//leader不一样时判断任期谁大
			if(param.getTerm()>currentTerm){
				this.currentTerm = param.getTerm();
				this.preHeartBeatTime=System.currentTimeMillis();
				this.leader=Peer.getPeer(param.getLeaderId());
				return AentryResult.builder().term(param.getTerm()).success(true).build();
			}else {
				return AentryResult.builder().term(this.currentTerm).success(false).build();
			}
		}
	}

	@Override
	public AentryResult handlerAppendEntries(AentryParam param) {
		if (param.getEntries() == null || param.getEntries().length == 0) {
			return this.handlerHeartbeat(param);
		}
		return null;
	}

	@Override
	public ClientKVAck handlerClientRequest(ClientKVReq request) {
		return null;
	}

	@Override
	public ClientKVAck redirect(ClientKVReq request) {
		String url = "http://" + leader.getPeerStr() + "?";
		String json =httpUtils.sendMsg(url + "/set", JSON.toJSONString(request));
		return JSON.parseObject(json, new TypeReference<ClientKVAck>() {});
	}
}
