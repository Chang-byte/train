package com.chang.train.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * @title: MemberApplication
 * @Author Chang
 * @Date: 2023-09-23 13:08
 * @Version 1.0
 */
@SpringBootApplication
public class MemberApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MemberApplication.class, args);

        // 获取配置文件里面的参数
        ConfigurableEnvironment environment = run.getEnvironment();
        LOG.info("启动成功!!");
        LOG.info("测试地址: \t http://127.0.0.1:{}{}/hello", environment.getProperty("server.port"), environment.getProperty("server.servlet.context-path"));

    }
}
