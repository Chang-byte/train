package com.chang.train.member.service;

import cn.hutool.core.collection.CollectionUtil;
import com.chang.train.exception.BusinessException;
import com.chang.train.exception.BusinessExceptionEnum;
import com.chang.train.member.domain.Member;
import com.chang.train.member.domain.MemberExample;
import com.chang.train.member.mapper.MemberMapper;
import com.chang.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据手机号注册用户
     * @param memberRegisterReq 用户注册的请求。
     * @return 注册成功的用户的Id。
     */
    public long register(MemberRegisterReq memberRegisterReq){
        String mobile = memberRegisterReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);

        if (CollectionUtil.isNotEmpty(list)){
//            throw new RuntimeException("用户已经存在!");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);

        return member.getId();

    }
}
