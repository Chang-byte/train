package com.chang.train.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: TestController
 * @Author Chang
 * @Date: 2023-09-23 13:09
 * @Version 1.0
 */
@RestController
public class TestController {

    /**
     *  测试热部署
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
