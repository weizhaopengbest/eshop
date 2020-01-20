package com.roncoo.eshop.cache.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author weizhaopeng
 * @date 2020/1/20
 */
public class ZookeeperSession {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private ZooKeeper zooKeeper;


    public ZookeeperSession() {
        try {
            this.zooKeeper = new ZooKeeper("eshop-cache01:2181,eshop-cache02:2181,eshop-cache03:2181",
                    50000, new ZookeeperWatcher());

            System.out.println("zooKeeper.getState() = " + zooKeeper.getState());

            try {
                connectedSemaphore.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void acquireDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;

        try {
            zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println(" acquireDistributedLock success productId:" + productId);

        } catch (Exception e) {
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(20);
                    zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                } catch (Exception ex) {
                    count++;
                    continue;
                }
                System.out.println(" acquireDistributedLock success productId:" + productId + " after num: " + count);
                break;
            }
        }
    }

    public void releaseDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;
        try {
            zooKeeper.delete(String.valueOf(productId), -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }


    private class ZookeeperWatcher implements Watcher {
        public void process(WatchedEvent event) {
            System.out.println("Receive watched event: " + event.getState());
            if (Event.KeeperState.SyncConnected == event.getState()) {
                connectedSemaphore.countDown();
            }
        }
    }

    /**
     * 封装单例的静态内部类
     */
    private static class Singleton {

        private static ZookeeperSession instance;

        static {
            instance = new ZookeeperSession();
        }

        public static ZookeeperSession getInstance() {
            return instance;
        }

    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ZookeeperSession getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化单例的便捷方法
     */
    public static void init() {
        getInstance();
    }


}
