package com.frozen.raft.controller;

import com.frozen.raft.aop.OperLog;
import com.frozen.raft.db.ClientKVAck;
import com.frozen.raft.db.ClientKVReq;
import com.frozen.raft.node.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NodeEventController {
	@Autowired
	DefualtNode defualtNode;

	@RequestMapping("/frozen")
	@OperLog
	public String test(){
		return "hello world";
	}
	/**
	 *
	 * @return
	 */
	@RequestMapping("/set")
	public ClientKVAck set(@RequestBody ClientKVReq request){
		log.info("NodeEventController clientRequest request{}",request);
		return defualtNode.handlerClientRequest(request);
	}


	/**
	 *
	 * @return
	 */
	@RequestMapping("/get")
	public String get(@RequestParam(name = "key") String key){
		log.info("NodeEventController clientRequest value={}",key);
		return "clientRequest";
	}


	/**
	 * 投票
	 * @return
	 */
	@RequestMapping("/rvote")
	public RvoteResult rvote(@RequestBody RvoteParam rvoteParam){
		return defualtNode.handlerRequestVote(rvoteParam);
	}

	/**
	 * 处理日志（包含心跳）
	 * @return
	 */
	@RequestMapping("/appendEntries")
	public AentryResult appendEntries(@RequestBody AentryParam aentryParam){
		return defualtNode.handlerHeartbeat(aentryParam);
	}


	/**
	 * 处理请求投票
	 * @return
	 */
	@RequestMapping("/requestVote")
	public RvoteResult requestVote(@RequestBody RvoteParam rvoteParam){
		return defualtNode.handlerRequestVote(rvoteParam);
	}
}
