package com.frozen.raft.rpc;

/**
 *
 * @author frozen
 */
public interface RpcClient {

    Response send(Request request);

}
