package com.curatortest;

import com.cluster.CuratorNodeCache;
import com.cluster.CuratorPathCache;
import com.cluster.CuratorTreeCache;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;

public class TestApacheCurator {
    //会话超时时间
    private final int SESSION_TIMEOUT = 30 * 1000;

    //连接超时时间
    private final int CONNECTION_TIMEOUT = 3 * 1000;

    //ZooKeeper服务地址
    private static final String SERVER = "192.168.118.129:2181";

    //创建连接实例
    private CuratorFramework client = null;

    /**
     * baseSleepTimeMs：初始的重试等待时间
     * maxRetries：最多重试次数
     */
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    @Before
    public void init() {
        //创建 CuratorFrameworkImpl实例
        client = CuratorFrameworkFactory.newClient(SERVER, SESSION_TIMEOUT, CONNECTION_TIMEOUT, retryPolicy);
        //启动
        client.start();
    }

    @Test
    public void testCuratorPathChildrenCache() throws Exception {
        new CuratorPathCache().start();
    }

    @Test
    public void testCuratorTreeCache() throws Exception {
        new CuratorTreeCache().start();
    }

    @Test
    public void testCuratorNodeCache() throws Exception {
        new CuratorNodeCache().start();
    }

    @Test
    public void testGetAllChildren() throws Exception {
        // 获取某节点下所有的节点
        System.out.println(client.getChildren().forPath("/curator"));
        System.out.println(client.getChildren().forPath("/curator/treeCache"));
        // 获取某节点的数据
        System.out.println(new String(client.getData().forPath("/curator/treeCache")));

        client.create().orSetData().forPath("/curator/treeCache", "500".getBytes());

        System.out.println(new String(client.getData().forPath("/curator/treeCache")));
    }

    @Test
    public void testOrSetData() throws Exception {
        // 使用creatingParentContainersIfNeeded()之后Curator能够自动递归创建所有所需的父节点
        client.create().orSetData().creatingParentContainersIfNeeded().forPath("/curator/testOrSetData", "hahahaha".getBytes());

        System.out.println(new String(client.getData().forPath("/curator/testOrSetData")));

        client.create().orSetData().creatingParentContainersIfNeeded().forPath("/curator/testOrSetDataForth/testOrSetDataForth", "testOrSetDataForth".getBytes());

        System.out.println(new String(client.getData().forPath("/curator/testOrSetData/testOrSetDataThird")));

        client.create().orSetData().creatingParentsIfNeeded().forPath("/curator/testOrSetData/testOrSetDataNext", "haha".getBytes());

        System.out.println(new String(client.getData().forPath("/curator/testOrSetData/testOrSetDataNext")));
    }

    @Test
    public void testDelete() throws Exception {
//        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator/testOrSetData");
//        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator/testOrSet");
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator/testOrSetDataForth");

    }

    /**
     * 测试TreeCache会不会监听自身的创建节点事件
     * @throws Exception
     */
    @Test
    public void testTreeCacheSelf() throws Exception {
        client.create().orSetData().creatingParentsIfNeeded().forPath("/curator/comic", "comicPath".getBytes());
    }

    /**
     * 测试TreeCache会不会监听自身的修改数据事件
     * @throws Exception
     */
    @Test
    public void testTreeCacheSelfUpdate() throws Exception {
        client.setData().forPath("/curator/comic", "comicPath1".getBytes());
    }

    /**
     * 测试pathChildrenCache会不会监听 监听的主节点
     * @throws Exception
     */
    @Test
    public void testPathChildrenCacheSelfUpdate() throws Exception {
        client.setData().forPath("/curator/pathChildrenCache", "comicPath333".getBytes());
    }

    /**
     * 测试pathChildrenCache会不会监听 子节点
     * @throws Exception
     */
    @Test
    public void testPathChildrenCacheChildrenUpdate() throws Exception {
        client.setData().forPath("/curator/pathChildrenCache/jordan", "top99".getBytes());
    }
}
