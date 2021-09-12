package com.task;

import com.task.mytask.MyTask;

public interface TaskManager {
    // 添加主任务
    public void addMainTask(MyTask myTask);

    // 删除任务
    public void deleteTask();

    // 修改任务
    public void updateTask();
}
