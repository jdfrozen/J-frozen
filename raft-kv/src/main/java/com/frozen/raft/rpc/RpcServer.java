package com.frozen.raft.rpc;

/**
 * @author frozen
 */
public interface RpcServer {

    void start();

    void stop();

    Response handlerRequest(Request request);

}
