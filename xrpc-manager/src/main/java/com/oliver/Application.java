package com.oliver;

import com.oliver.utils.zookeeper.ZookeeperNode;
import com.oliver.utils.zookeeper.ZookeeperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Registration center management page
 *
 * @author xiaorui
 */
@Slf4j
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


        // create a zookeeper instance
        ZooKeeper zooKeeper = ZookeeperUtil.createZookeeper();

        // Define data and nodes
        String basePath = "xrpc-metadata";
        String providerPath = basePath + "/providers";
        String consumerPath = basePath + "/consumers";
        ZookeeperNode baseNode = new ZookeeperNode(basePath, null);
        ZookeeperNode providerNode = new ZookeeperNode(providerPath, null);
        ZookeeperNode consumerNode = new ZookeeperNode(consumerPath, null);

        // Create nodes
        List.of(baseNode, providerNode, consumerNode).forEach(node -> {
            ZookeeperUtil.createNode(zooKeeper, node, null, CreateMode.PERSISTENT);
        });

        ZookeeperUtil.close(zooKeeper);
    }
}
