package com.chang.train.member.controller;

import com.chang.train.member.req.MemberRegisterReq;
import com.chang.train.member.service.MemberService;
import com.chang.train.resp.CommonResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
    public CommonResp<Integer> count() {
        System.out.println(memberService);
        int count = memberService.count();
        return new CommonResp<Integer>(count);
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq memberRegisterReq){
        long register = memberService.register(memberRegisterReq);
        return new CommonResp<Long>(register);
    }
}
