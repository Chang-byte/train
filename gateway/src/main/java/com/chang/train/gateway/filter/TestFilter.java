package com.chang.train.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @title: TestFilter
 * @Author Chang
 * @Date: 2023-09-24 19:38
 * @Version 1.0
 */
//@Component 想要使用的话，必须加上这个注解。
public class TestFilter implements GlobalFilter {
    private static final Logger LOG = LoggerFactory.getLogger(TestFilter.class);

    /**
     * Mono 事件编程 / 响应式编程。
     * 过滤器。
     * 在这个方法中，可以获取全局的请求的参数。
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    @Order(1)  // 多个过滤器的执行顺序。
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.info("TestFilter");


        // The Mono returned by the supplier is null

        // 过滤器放行: return chain.filter(exchange);
        return chain.filter(exchange);
        // 过滤器不放行: return null;
//        return null;
    }
}
