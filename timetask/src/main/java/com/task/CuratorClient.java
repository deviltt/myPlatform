package com.task;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CuratorClient {
    @Value("192.168.2.135:2181")
    private String SERVER;

    public CuratorFramework getClient() {
        return CuratorFrameworkFactory.newClient(SERVER, new ExponentialBackoffRetry(1000, 3));
    }
}
