package com.frozen.raft.node;


import com.frozen.raft.common.NodeConfig;
import com.frozen.raft.dbreq.ClientKVAck;
import com.frozen.raft.dbreq.ClientKVReq;
import com.frozen.raft.entity.AentryParam;
import com.frozen.raft.entity.AentryResult;
import com.frozen.raft.entity.RvoteParam;
import com.frozen.raft.entity.RvoteResult;
import com.frozen.raft.node.LifeCycle;

/**
 *
 * @author frozen
 */
public interface Node<T> extends LifeCycle {

    /**
     * 设置配置文件.
     *
     * @param config
     */
    void setConfig(NodeConfig config);

    /**
     * 处理请求投票 RPC.
     *
     * @param param
     * @return
     */
    RvoteResult handlerRequestVote(RvoteParam param);

    /**
     * 处理附加日志请求.
     *
     * @param param
     * @return
     */
    AentryResult handlerAppendEntries(AentryParam param);

    /**
     * 处理客户端请求.
     *
     * @param request
     * @return
     */
    ClientKVAck handlerClientRequest(ClientKVReq request);

    /**
     * 转发给 leader 节点.
     * @param request
     * @return
     */
    ClientKVAck redirect(ClientKVReq request);

}
