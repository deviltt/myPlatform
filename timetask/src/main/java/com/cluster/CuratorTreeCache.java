package com.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class CuratorTreeCache {
    private static final String ADDRESS = "192.168.2.135:2181";

    private static final String PATH = "/curator/comic";

    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void start() throws Exception {
        // 建立连接
        final CuratorFramework client = CuratorFrameworkFactory.newClient(ADDRESS, new ExponentialBackoffRetry(1000, 3));

        client.start();

        // 建立监听器，PATH下面所有的节点都会监听到
        final TreeCache treeCache = new TreeCache(client, PATH);

        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                // 监听 PATH 节点的子节点的变动情况，打印出事件以及节点下的数据
                System.out.println("==== catch /curator/comic change ====");
                System.out.println("event : " + event);
                System.out.println("update event type : " + event.getType() + ", path : " + event.getData().getPath()
                        + ", data : " + new String(event.getData().getData(), CHARSET));

                ChildData childData = treeCache.getCurrentData(PATH);
                if (childData != null) {
                    System.out.println("path : " + childData.getPath() + ", " + new String(childData.getData(), CHARSET));
                }

                System.out.println();
            }
        });

        treeCache.start();

        TimeUnit.MINUTES.sleep(10);

        treeCache.close();
    }
}
