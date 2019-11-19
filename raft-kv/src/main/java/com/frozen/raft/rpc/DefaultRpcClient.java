package com.frozen.raft.rpc;

import com.alipay.remoting.exception.RemotingException;
import com.frozen.raft.exception.RaftRemotingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 莫那·鲁道
 */
@Slf4j
public class DefaultRpcClient implements RpcClient {
    private final static com.alipay.remoting.rpc.RpcClient CLIENT = new com.alipay.remoting.rpc.RpcClient();
    static {
        CLIENT.init();
    }
    @Override
    public Response send(Request request) {
        Response result = null;
        try {
            result = (Response) CLIENT.invokeSync(request.getUrl(), request, 200000);
        } catch (RemotingException e) {
            e.printStackTrace();
            log.info("rpc RaftRemotingException ");
            throw new RaftRemotingException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
