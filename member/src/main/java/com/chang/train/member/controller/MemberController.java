package com.chang.train.member.controller;

import com.chang.train.member.req.MemberLoginReq;
import com.chang.train.member.req.MemberRegisterReq;
import com.chang.train.member.req.MemberSendCodeReq;
import com.chang.train.member.resp.MemberLoginResp;
import com.chang.train.member.service.MemberService;
import com.chang.train.resp.CommonResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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


    /**
     *
     * 用户登录: 使用图形验证码防止黑客频繁刷接口，进行短信攻击。
     * 校验短信频繁，保存短信记录表。(todo: 后端需要考虑)
     * 1. 图形验证码， 请求发起图形验证码请求。
     * 2. 后端生成验证码， 保存验证码到缓存中。
     * 3. 返回验证码的图片。(应该返回图片,而不是验证码字符串)
     *
     * 输入手机号  +  图像验证码。
     * 后端接口参数校验，图形验证码校验。
     * 手机号是否注册过?(没有的话，就直接注册了)
     * 生成默认的短信的验证码，前端去做60s的短信验证码的再请求。
     * 最后生成Jwt，登录。
     */
    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq req){
        memberService.sendCode(req);
        return new CommonResp<>();
    }


    /**
     * 单点登录： redis + token; jwt
     *
     * redis + token:
     * 登录开始，校验用户名和密码，生成随机token，每次都不一样，将token存入到redis。
     * 校验开始：从header中获取token，根据token去redis中查询，如果存在登录成功，否则，不成功。
     *
     *
     * jwt：
     * 不需要从redis中获取，因为jwt本身就是根据特定的规则 + 用户的信息
     * 生成字符串，就能直接定位某一个具体的用户。 使用工具包校验token，反向解密信息。
     * 存在的问题：
     *  1.token被解密? 加盐值。 每个项目的盐值不能一样。
     *  2.token被拿到第三方使用？ 自己的产品，被别人包成一个界面，做成他们收费的产品。 ? 限流
     * @param req
     * @return
     */
    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req) {
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
        /**
         * 使用线程本地变量。
         * 在接口的入口获取会员的信息，并放到线程本地变量，则在Controller、service都可以
         * 直接从线程本地变量中获取会员信息，而不用再去从数据库中查询。
         * 因为好多都需要memberId，放在common模块中。
         */
    }


}
