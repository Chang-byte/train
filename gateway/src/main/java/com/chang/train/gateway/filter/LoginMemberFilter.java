package com.chang.train.gateway.filter;

import com.chang.train.gateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @title: LoginMemberFilter 会员登录的过滤器。
 * @Author Chang
 * @Date: 2023-09-24 19:45
 * @Version 1.0
 */
@Component
public class LoginMemberFilter implements GlobalFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberFilter.class);

    @Override
    @Order(0) // 值越小，优先级越高。
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        /**
         * GET http://localhost:8000/member/member/count 会被拦截掉。 注意必须是网关的这个请求。
         */
        // 排除不需要拦截的请求 白名单
        if (path.contains("/admin")
                || path.contains("/redis")
                || path.contains("/hello")
                || path.contains("/member/member/login")
                || path.contains("/member/member/send-code")
                || path.contains("/business/kaptcha")) {
            LOG.info("不需要登录验证：{}", path);
            // 放行
            return chain.filter(exchange);
        } else {
            LOG.info("需要登录验证：{}", path);
        }
        // 获取header的token参数， 校验token 从request的header中获取。
        String token = exchange.getRequest().getHeaders().getFirst("token");
        LOG.info("会员登录验证开始，token：{}", token);
        if (token == null || token.isEmpty()) {
            LOG.info("token为空，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 结束请求，拦截住。
            return exchange.getResponse().setComplete();
        }

        // 校验token是否有效，包括token是否被改过，是否过期
        boolean validate = JwtUtil.validate(token);
        if (validate) {
            LOG.info("token有效，放行该请求");
            return chain.filter(exchange);
        } else {
            LOG.warn("token无效，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

}
