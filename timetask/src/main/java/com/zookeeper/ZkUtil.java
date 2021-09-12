package com.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 功能：方便快捷的创建节点和建立连接
 */
public class ZkUtil {
    public static CuratorFramework getClientWithExponentialRetry(String server, int sleepTimeMs, int maxRetries) {
        return CuratorFrameworkFactory.newClient(server, new ExponentialBackoffRetry(sleepTimeMs, maxRetries));
    }
}
