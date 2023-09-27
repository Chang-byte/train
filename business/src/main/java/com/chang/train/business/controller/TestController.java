package com.chang.train.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
