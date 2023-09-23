package com.chang.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.chang.train.exception.BusinessException;
import com.chang.train.exception.BusinessExceptionEnum;
import com.chang.train.member.domain.Member;
import com.chang.train.member.domain.MemberExample;
import com.chang.train.member.mapper.MemberMapper;
import com.chang.train.member.req.MemberLoginReq;
import com.chang.train.member.req.MemberRegisterReq;
import com.chang.train.member.req.MemberSendCodeReq;
import com.chang.train.member.resp.MemberLoginResp;
import com.chang.train.util.JwtUtil;
import com.chang.train.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

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
        Member memberDB = selectByMobile(mobile);

        if (ObjectUtil.isNull(memberDB)) {
            // return list.get(0).getId();
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

    /**
     * 注册， 或者登录的时候发送验证码
     *
     * @param req
     */
    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectByMobile(mobile);

        // 如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        } else {
            LOG.info("手机号存在，不插入记录");
        }

        // 生成验证码
        // String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码：{}", code);

        // 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间/
        // todo 使用Redis去存储用户的短信的验证码。 黑马点评项目 使用 RedisLimiter 去做限流，防止用户短时间内发送大量的请求。
        LOG.info("保存短信记录表");

        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }

    /**
     * 根据手机号去查询用户是否已经注册过，是否存在这个用户。
     * 抽取成公用的方法。
     * @param mobile
     * @return
     */
    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 用户登录
     * @param req
     * @return
     */
    public MemberLoginResp login(MemberLoginReq req) {
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectByMobile(mobile);

        // 如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        if (!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        // 数据脱敏
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        // 创建token
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }
}
