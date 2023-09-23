package com.chang.train.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;


    @Test
    public void test(){
        int count = memberService.count();
        System.out.println(count);
    }

}