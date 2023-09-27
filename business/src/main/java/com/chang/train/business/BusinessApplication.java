package com.chang.train.business;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @title: BusinessApplication
 * @Author Chang
 * @Date: 2023-09-27 19:09
 * @Version 1.0
 */
@SpringBootApplication
@ComponentScan("com.chang")
@MapperScan("com.chang.train.*.mapper")
public class BusinessApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BusinessApplication.class, args);

        // 获取配置文件里面的参数
        ConfigurableEnvironment environment = run.getEnvironment();
        LOG.info("启动成功!!");
        LOG.info("测试地址: \t http://127.0.0.1:{}{}/hello", environment.getProperty("server.port"), environment.getProperty("server.servlet.context-path"));

    }
}
