package com.chang.train.batch.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @title: job
 * @Author Chang
 * @Date: 2023-09-29 14:36
 * @Version 1.0
 */
public class TestJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("job test!");

    }
}
