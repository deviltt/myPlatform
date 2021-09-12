package com.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CuratorPathCache {
    private static final String ADDRESS = "192.168.2.135:2181";

    private static final String PATH = "/curator/pathChildrenCache";

    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void start() throws Exception {
        final CuratorFramework client = CuratorFrameworkFactory.newClient(ADDRESS, new ExponentialBackoffRetry(1000, 3));

        client.start();

        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, PATH, true);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                // 监听 PATH 节点的子节点的变动情况，打印出事件以及节点下的数据
                System.out.println("==== catch /curator/pathChildrenCache change ====");
                System.out.println("event : " + event);
                System.out.println("update event type : " + event.getType() + ", path : " + event.getData().getPath()
                        + ", data : " + new String(event.getData().getData(), CHARSET));

                List<ChildData> childDataList = pathChildrenCache.getCurrentData();
                if (childDataList != null && childDataList.size() > 0) {
                    System.out.println("path all children list : ");
                    for (int i = 0; i < childDataList.size(); i++) {
                        System.out.println("path : " + childDataList.get(i).getPath() + ", " + new String(childDataList.get(i).getData(), CHARSET));
                    }
                }
                System.out.println();
            }
        });

        // 启动监听器
        pathChildrenCache.start();

        TimeUnit.MINUTES.sleep(10);

        pathChildrenCache.close();
    }
}
