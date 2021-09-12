package com.cluster;

import com.zookeeper.ZkUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class CuratorNodeCache {
    // 连接地址
    private static final String ADDRESS = "192.168.118.129:2181";

    // 路径
    private static final String PATH = "/curator/nodeCache/test";
    private static final String PERSISTENT_SEQUENTIAL_PATH = "/curator/nodeCache/persistentSequenceTest";
    private static final String PERSISTENT_PATH = "/curator/nodeCache/persistentTest";
    private static final String EPHEMERAL_PATH = "/curator/nodeCache/ephemeralTest";

    // 字符格式
    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void start() throws Exception {
        // 建立连接
        final CuratorFramework client = ZkUtil.getClientWithExponentialRetry(PATH, 1000, 3);
        // 开启连接
        client.start();

        // 创建监听器
        final NodeCache nodeCache = new NodeCache(client, PATH);

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = nodeCache.getCurrentData();
                if (childData != null) {
                    System.out.println(new String(childData.getData(), CHARSET));
                }
            }
        });
        nodeCache.start();
        TimeUnit.SECONDS.sleep(3);

        System.out.println("--------create--------");
        if (client.checkExists().forPath(PATH) != null) {
            client.delete().forPath(PATH);
        }
        client.create().forPath(PATH, "tt".getBytes());
        System.out.println(client.checkExists().forPath(PATH).getEphemeralOwner());


        // 创建无序持久节点
        System.out.println("创建无序持久节点");
        if (client.checkExists().forPath(PERSISTENT_PATH) != null) {
            client.delete().forPath(PERSISTENT_PATH);
        }
        client.create().withMode(CreateMode.PERSISTENT).forPath(PERSISTENT_PATH, "tt".getBytes());
        System.out.println(client.checkExists().forPath(PERSISTENT_PATH).getEphemeralOwner());

        // 创建有序持久节点
        System.out.println("创建临时节点");
        if (client.checkExists().forPath(EPHEMERAL_PATH) != null) {
            client.delete().forPath(EPHEMERAL_PATH);
        }
        client.create().withMode(CreateMode.EPHEMERAL).forPath(EPHEMERAL_PATH, "ttEphemeral".getBytes());
        System.out.println(client.checkExists().forPath(EPHEMERAL_PATH).getEphemeralOwner());
        // 有序节点会自动生成编号，所以原来的序列路径就不管用了
//        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(PERSISTENT_SEQUENTIAL_PATH, "tt".getBytes());
//        System.out.println(client.checkExists().forPath("/curator/nodeCache/persistentSequenceTest0000000009").getEphemeralOwner());

        TimeUnit.SECONDS.sleep(5);

        System.out.println("--------update--------");
        client.setData().forPath(PATH, "zz".getBytes());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("--------query--------");
        client.getData().forPath(PATH);

        TimeUnit.SECONDS.sleep(3);

        nodeCache.close();
        client.close();
    }


}
