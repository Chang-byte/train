package com.chang.train.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: GatewayApplication
 * @Author Chang
 * @Date: 2023-09-23 14:33
 * @Version 1.0
 */

/**
 * 生产发布时，只有gateway需要配置外网ip，其他模块都只开放内网访问，外网访问不了，保证应用安全。
 * 请求gateway，然后通过路由转发，转发到对应的内网模块。
 *
 * 添加VM启动参数，可以看到转发的日志控制台输出。
 * -Dreactor.netty.http.server.accessLogEnabled=True
 * 47:54.142 INFO  r.netty.http.server.AccessLog :279  reactor-http-nio-2
 * 127.0.0.1 - - [23/9月/2023:14:47:53 +0800] "GET /member/hello HTTP/1.1" 200 12 186
 */
@SpringBootApplication
public class GatewayApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        LOG.info("网关启动成功!!");
    }
}
