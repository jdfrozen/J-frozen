package com.frozen.raft.rpc;

/**
 * @author 莫那·鲁道
 */
public interface RpcServer {

    void start();

    void stop();

    Response handlerRequest(Request request);

}
