package com.oliver;


import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Registration center management page
 * @author xiaorui
 */
public class Application {
    public static void main(String[] args) {
        // Help us create a basic directory
        // xrpc-metadata   (Persistent node)
        //  └─ providers （Persistent node）
        //  		└─ service1  （Persistent node，Fully qualified name of the interface）
        //  		    ├─ node1 [data]     /ip:port
        //  		    ├─ node2 [data]
        //            └─ node3 [data]
        //  └─ consumers
        //        └─ service1
        //             ├─ node1 [data]
        //             ├─ node2 [data]
        //             └─ node3 [data]
        //  └─ config

        // Create a zookeeper instance
        ZooKeeper zooKeeper;
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // Define connection parameters
        String connectString = Constant.DEFAULT_ZK_CONNECT;
        // Define the timeout
        int timeout = Constant.TIME_OUT;

        try {
            // Create a zookeeper instance and establish a connection
            zooKeeper = new ZooKeeper(connectString, timeout, event -> {
                // √The connection is not released until it is successful
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("Client connected successfully");
                    countDownLatch.countDown();
                }
            });

            countDownLatch.await();
            // Define nodes and data
            String basePath = "xrpc-metadata";
            String providerPath = basePath + "/providers";
            String consumerPath = basePath + "/consumers";

            if (zooKeeper.exists(basePath, null) == null) {
                zooKeeper.create(basePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists(providerPath, null) == null) {
                zooKeeper.create(providerPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists(consumerPath, null) == null) {
                zooKeeper.create(consumerPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (IOException | InterruptedException | KeeperException e) {
            throw new RuntimeException(e);
        }
    }
}
