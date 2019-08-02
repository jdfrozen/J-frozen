package com.frozen.zookeeperclient.client;

import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

/**
 * @author: Frozen
 * @create: 2019-08-01 20:10
 * @description: zk客户端1
 **/
public class ZkClient3 {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("192.168.168.133:2181");//建立连接
        zkClient.create("/root", "mydata", CreateMode.PERSISTENT);//创建目录并写入数据
        String data = zkClient.readData("/root");
        System.out.println(data);
        //zkClient.delete("/root");//删除目录
        //zkClient.deleteRecursive("/root");//递归删除节目录
    }

}
