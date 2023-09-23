package com.chang.train.member.service;

import cn.hutool.core.collection.CollectionUtil;
import com.chang.train.exception.BusinessException;
import com.chang.train.exception.BusinessExceptionEnum;
import com.chang.train.member.domain.Member;
import com.chang.train.member.domain.MemberExample;
import com.chang.train.member.mapper.MemberMapper;
import com.chang.train.member.req.MemberRegisterReq;
import com.chang.train.util.SnowUtil;
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

    public int count() {
        System.out.println(memberMapper);
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    /**
     * 根据手机号注册用户
     *
     * @param memberRegisterReq 用户注册的请求。
     * @return 注册成功的用户的Id。
     */
    public long register(MemberRegisterReq memberRegisterReq) {
        String mobile = memberRegisterReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);

        if (CollectionUtil.isNotEmpty(list)) {
//            throw new RuntimeException("用户已经存在!");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        /*
         * 1.自增id不适合分布式数据库，分库分表场景，适合小型项目。
         * 2.UUID会影响索引效率。因为UUID是无序的，用一堆无序的ID来构建一个有序的索引目录，性能上是会出问题的。
         * 3.雪花算法：生成的ID是不断增长的，也不会重复的。 long类型
         *  全局唯一，有序增长，生成效率高。
         *  1bit不用， 41bit时间戳(保证增长)  10bit 工作机器id  12bit序列号。
         *  数据中心，机器ID怎么设置?
         *      利用Redis自增序列。
         *      利用数据库，为每台机器分配workid，保存ip和workid的关系
         *  时钟回拨?
         */
//        member.setId(System.currentTimeMillis());
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);

        return member.getId();

    }
}
