package com.oliver.utils.zookeeper;

import com.oliver.Constant;
import com.oliver.exceptions.ZookeeperException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaorui
 */
@Slf4j
public class ZookeeperUtil {
    /**
     * Create a zookeeper instance with default config
     * @return zookeeper instance
     */
    public static ZooKeeper createZookeeper() {
        // Define connection parameters
        String connectString = Constant.DEFAULT_ZK_CONNECT;
        // Define the timeout
        int timeout = Constant.TIME_OUT;

        return createZookeeper(connectString, timeout);
    }

    /**
     * Create a zookeeper instance with customized config
     * @param connectString zookeeper address
     * @param timeout zookeeper timeout
     * @return zookeeper instance
     */
    public static ZooKeeper createZookeeper(String connectString, int timeout) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            // Create a zookeeper instance and establish a connection
            final ZooKeeper zooKeeper = new ZooKeeper(connectString, timeout, event -> {
                // The connection is not released until it is successful
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("Client connected successfully");
                    countDownLatch.countDown();
                }
            });

            countDownLatch.await();
            return zooKeeper;
        } catch (IOException | InterruptedException e) {
            log.error("Exception for creating a zookeeper instance", e);
            throw new ZookeeperException();
        }
    }

    /**
     * Create a zookeeper node
     * @param zooKeeper zookeeper instance
     * @param zookeeperNode customized node
     * @param watcher watcher instance
     * @param createMode createMode
     * @return true: create node successfully, false: node exists, exception
     */
    public static Boolean createNode(ZooKeeper zooKeeper, ZookeeperNode zookeeperNode, Watcher watcher, CreateMode createMode) {
        try {
            if (zooKeeper.exists(zookeeperNode.getNodePath(), watcher) == null) {
                String result = zooKeeper.create(zookeeperNode.getNodePath(), zookeeperNode.getData(), ZooDefs .Ids.OPEN_ACL_UNSAFE, createMode);
                log.info("Node [{}] created successfully", result);
                return true;
            } else {
                if(log.isDebugEnabled()) {
                    log.info("Node [{}] exists successfully", zookeeperNode.getNodePath());
                }
                return false;
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("Exception for creating basic dictionary:", e);
            throw new ZookeeperException();
        }
    }

    /**
     * Close zookeeper
     * @param zooKeeper zookeeper instance
     */
    public static void close(ZooKeeper zooKeeper) {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            log.error("Exception for closing zookeeper ", e);
            throw new ZookeeperException();
        }
    }
}
