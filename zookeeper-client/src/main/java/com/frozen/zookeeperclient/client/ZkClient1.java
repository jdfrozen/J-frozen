package com.frozen.zookeeperclient.client;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author: Frozen
 * @create: 2019-08-01 20:10
 * @description: zk客户端1
 **/
public class ZkClient1 {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("192.168.0.183:2181", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.toString());
            }
        });
        System.out.println("OK!");
        // 创建一个目录节点
        /**
         * CreateMode:
         *       PERSISTENT (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
         *       PERSISTENT_SEQUENTIAL（持久的且带顺序的）
         *       EPHEMERAL (短暂的，生命周期依赖于client session)
         *       EPHEMERAL_SEQUENTIAL  (短暂的，带顺序的)
         */
        zk.create("/country", "China".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/country/city", "China/Hangzhou".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/country", false, null)));
        // 取出子目录节点列表
        System.out.println(zk.getChildren("/country", true));
        // 创建另外一个子目录节点
        zk.create("/country/view", "China/WestLake".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zk.getChildren("/country", true));
        // 修改子目录节点数据
        zk.setData("/country/city", "China/Shanghai".getBytes(), -1);
        byte[] datas = zk.getData("/country/city", true, null);
        String str = new String(datas, "utf-8");
        System.out.println(str);
        // 删除整个子目录 -1代表version版本号，-1是删除所有版本
//        zk.delete("/path01/path01", -1);
//        zk.delete("/path01/path02", -1);
//        zk.delete("/path01", -1);
//        System.out.println(str);
        Thread.sleep(15000);
        zk.close();
        System.out.println("OK");
    }

}
