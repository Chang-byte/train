package com.chang.train.member.config;

import com.chang.train.interceptor.LogInterceptor;
import com.chang.train.interceptor.MemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @title: SpringMvcConfig
 * @Author Chang
 * @Date: 2023-09-27 13:43
 * @Version 1.0
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    MemberInterceptor memberInterceptor;

    @Resource
    LogInterceptor logInterceptor;

    /**
     * 添加一个拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 前后代表顺序。 增加日志拦截器，统一打印日志流水号

        // 全部请求，都需要
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(memberInterceptor)
                // 针对所有的请求的接口来实现。
                .addPathPatterns("/**")
                .excludePathPatterns("/member/member/login",
                        "/member/member/send-code");
    }



}
