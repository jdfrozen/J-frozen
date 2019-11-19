package com.frozen.raft.rpc;

/**
 *
 * @author 莫那·鲁道
 */
public interface RpcClient {

    Response send(Request request);

}
