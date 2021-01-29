package com.frozen.raft.node;

import com.frozen.raft.db.ClientKVAck;
import com.frozen.raft.db.ClientKVReq;

/**
 *
 * @author frozen
 */

public interface Node<T> {

	/**
	 * 节点上线
	 * @throws Throwable
	 */
	void init() throws Throwable;

	/**
	 *  节点自我下线
	 * @throws Throwable
	 */
	void destroy() throws Throwable;

	/**
	 * 处理请求投票 RPC.
	 *
	 * @param param
	 * @return
	 */
	RvoteResult handlerRequestVote(RvoteParam param);

    /**
     * 处理附加日志请求.（包含心跳）
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
