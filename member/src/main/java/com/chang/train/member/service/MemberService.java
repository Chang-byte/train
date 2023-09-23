package com.chang.train.member.service;

import com.chang.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @title: MemberService
 * @Author Chang
 * @Date: 2023-09-23 16:00
 * @Version 1.0
 */
@Service
public class MemberService {

    @Resource
    private MemberMapper memberMapper;

    public int count(){
        System.out.println(memberMapper);
        return Math.toIntExact(memberMapper.countByExample(null));
    }
}
