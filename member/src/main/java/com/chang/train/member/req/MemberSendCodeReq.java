package com.chang.train.member.req;

/**
 * @title: MemberSendCodeReq
 * @Author Chang
 * @Date: 2023-09-23 20:31
 * @Version 1.0
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 发送短信验证码的请求类
 */
public class MemberSendCodeReq {
    @NotBlank(message = "[手机号不能为空]")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "[手机号格式错误]")
    private String mobile;


    public MemberSendCodeReq(String mobile) {
        this.mobile = mobile;
    }

    public MemberSendCodeReq() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
