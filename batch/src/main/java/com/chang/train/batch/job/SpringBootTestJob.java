package com.chang.train.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @title: SpringBootTestJob
 * @Author Chang
 * @Date: 2023-09-29 14:27
 * @Version 1.0
 */
/*
 自带的定时任务，只适合单体应用，不适合集群。
 没法实时更改定时任务状态和策略。 适合小型任务
 */
//@Component
@EnableScheduling // 开启定时任务
public class SpringBootTestJob {

    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
        System.out.println("SpringBootTestJob.test");
    }
}
