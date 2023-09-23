package com.chang.train.member.controller;

import com.chang.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: MemberController
 * @Author Chang
 * @Date: 2023-09-23 16:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    MemberService memberService;

    @GetMapping("/count")
    public String count() {
        System.out.println(memberService);
        int count = memberService.count();
        System.out.println(count);
        return "count:" + 1;
    }

    @PostMapping("/register")
    public long register(String mobile){
        return memberService.register(mobile);
    }
}
