package com.chang.train.member.req;

/**
 * @title: MemberRegisterReq
 * @Author Chang
 * @Date: 2023-09-23 17:34
 * @Version 1.0
 */
public class MemberRegisterReq {

    private String mobile;

    public MemberRegisterReq() {
    }

    public MemberRegisterReq(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
