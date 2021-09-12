package com.jobmanager;

import org.quartz.Job;

import java.util.List;

/**
 * job管理所具备的能力
 */
public interface JobManagerSpi {
    // 增

    // 删

    // 改

    // 查
    public List<Job> getAllJobs();
}
