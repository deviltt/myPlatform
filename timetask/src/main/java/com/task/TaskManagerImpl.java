package com.task;

import com.task.mytask.MyTask;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskManagerImpl implements TaskManager {
    @Autowired
    private CuratorClient curatorClient;

    @Override
    public void addMainTask(MyTask myTask) {
        CuratorFramework client = curatorClient.getClient();

        // 创建主任务节点 /curator/add
//        client.create().forPath()
    }

    @Override
    public void deleteTask() {

    }

    @Override
    public void updateTask() {

    }
}
