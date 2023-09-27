package com.chang.train.member.controller;

import com.chang.train.context.LoginMemberContext;
import com.chang.train.member.req.PassengerQueryReq;
import com.chang.train.member.req.PassengerSaveReq;
import com.chang.train.member.resp.PassengerQueryResp;
import com.chang.train.member.service.PassengerService;
import com.chang.train.resp.CommonResp;
import com.chang.train.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @title: PassengerController
 * @Author Chang
 * @Date: 2023-09-24 20:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerController.class);

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req) {
        LOG.info("PassengerController.save() req:{}", req);
        passengerService.save(req);
        return new CommonResp<>();
    }


    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        Long id = LoginMemberContext.getId();
        req.setMemberId(id);
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);
         return new CommonResp<>(list);
    }
}
