package com.frozen.raft;

import com.frozen.raft.rpc.*;
import org.junit.jupiter.api.Test;

/**
 * @author: Frozen
 * @create: 2019-11-19 13:25
 * @description:
 **/
public class RpcTest {
	@Test
	public void TestRpc(){
		RpcServer RPC_SERVER=new DefaultRpcServer(8088);
		RPC_SERVER.start();
		RpcClient rpcClient = new DefaultRpcClient();
		Request request = Request.newBuilder().url("localhost:8088").build();
		Response response = rpcClient.send(request);
		RPC_SERVER.stop();
	}
}
