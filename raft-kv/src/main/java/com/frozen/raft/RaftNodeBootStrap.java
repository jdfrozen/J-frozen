package com.frozen.raft;

import com.frozen.raft.common.NodeConfig;
import com.frozen.raft.impl.DefaultNode;
import com.frozen.raft.node.Node;

import java.util.Arrays;

/**
 * -DserverPort=8775
 * -DserverPort=8776
 * -DserverPort=8777
 * -DserverPort=8778
 * -DserverPort=8779
 */
public class RaftNodeBootStrap {

    public static void main(String[] args) throws Throwable {
        main0();
    }

    public static void main0() throws Throwable {
        //节点固定，若想节点可以动态，可以做成配置文件，或者再引入个配置中心？？？
        String[] peerAddr = {"localhost:8775","localhost:8776","localhost:8777", "localhost:8778", "localhost:8779"};
        NodeConfig config = new NodeConfig();
        // 自身节点
        config.setSelfPort(Integer.valueOf(System.getProperty("serverPort")));
        // 其他节点地址
        config.setPeerAddrs(Arrays.asList(peerAddr));
        Node node = DefaultNode.getInstance();
        node.setConfig(config);
        node.init();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                node.destroy();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }));

    }

}
