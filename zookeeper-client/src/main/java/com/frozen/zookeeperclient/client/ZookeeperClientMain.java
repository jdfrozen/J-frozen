package com.frozen.zookeeperclient.client;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: frozen
 * @Date: 2019/3/10 21:39
 * @Description: zookeeper 客户端测试
 */

public class ZookeeperClientMain {
    private static final String ADDRESS = "127.0.0.1:2181";

    private static final Integer SESSION_TIMEOUT = 2000;
    //信号量,阻塞程序执行,用户必须等待zookeeper连接成功,发送成功信号
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    private ZooKeeper zk;

    public static void main(String[] args) {
        ZookeeperClientMain zkclient = new ZookeeperClientMain();
        //connection to zkserver
        zkclient.createConnection(ADDRESS, SESSION_TIMEOUT);
        zkclient.createNode("/frozen","jdfrozen");
        zkclient.deleteNode("/frozen");
        //关闭zk链接
        zkclient.close();
    }

    /**
     * 创建连接
     *
     * @param address
     * @param sessionTimeout
     */
    public void createConnection(String address, Integer sessionTimeout) {
        try {
            zk = new ZooKeeper(address, sessionTimeout, new TestWatcher());
            System.out.println("###zookeeper启动连接服务器###");
        } catch (IOException e) {
            System.out.println("###zookeeper启动连接服务器###"+e);
        }
    }

    /**
     * 关闭服务
     */
    public void close() {
        try {
            if (zk != null) {
                System.out.println("###zookeeper服务已关闭");
                zk.close();
            }
        } catch (Exception e) {
            System.out.println("###zookeeper服务关闭异常"+ e);
        }
    }

    /**
     * 创建持久化节点
     * @param path
     * @param data
     * @return
     */
    public boolean createNode(String path, String data) {
        this.exist(path, true);
        try {
            //COUNT_DOWN_LATCH.wait();
            zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("###新增节点信息path={}, data={}"+  path+ data);
            return true;
        } catch (Exception e) {
            System.out.println("###新增节点信息异常path={}, data={}"+ path+  data+  e);
            return false;
        }
    }
    /**
     * 修改持久化节点
     * @param path
     * @param data
     */
    public boolean updateNode(String path, String data){
        try {
            this.exist(path,true);
            //阻塞，当等于0的时候释放
            //COUNT_DOWN_LATCH.await();
            //zk的数据版本是从0开始计数的。如果客户端传入的是-1，则表示zk服务器需要基于最新的数据进行更新。如果对zk的数据节点的更新操作没有原子性要求则可以使用-1.
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zk.setData(path,data.getBytes(),-1);
            System.out.println("###修改节点信息path={}, data={}"+ path+  data);
            return true;
        } catch (Exception e) {
            System.out.println("###修改节点信息path={}, data={}"+  path+  data+e);
            return false;
        }
    }

    /**
     * 判断指定节点是否存在
     *
     * @param path
     * @param needWatch
     * @return
     */
    public Stat exist(String path, boolean needWatch) {
        try {
            return this.zk.exists(path, needWatch);
        } catch (Exception e) {
            System.out.println("###zookeeper节点存在判断异常"+ e);
        }
        return null;
    }

    /**
     * 删除持久化节点
     *
     * @param path
     * @return
     */
    public boolean deleteNode(String path) {
        this.exist(path, true);
        try {
            //COUNT_DOWN_LATCH.await();
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zk.delete(path, -1);
            System.out.println("###删除节点信息path:" + path);
            return true;
        } catch (Exception e) {
            System.out.println("###删除节点信息失败path={}"+ path+ e);
            return false;
        }
    }

    /**
     * 监听器
     */
    static class TestWatcher implements Watcher {
        Logger log = Logger.getLogger(Watcher.class);
        @Override
        public void process(WatchedEvent event) {
            // TODO Auto-generated method stub
            System.out.println("*****************************");
            System.out.println("path:  " + event.getPath());
            System.out.println("type:  " + event.getType());
            System.out.println("state:  " + event.getState());
            System.out.println("*****************************");
        }
    }
}
