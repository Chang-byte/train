package com.chang.train.business.controller.admin;

import com.chang.train.business.req.TrainSeatQueryReq;
import com.chang.train.business.req.TrainSeatSaveReq;
import com.chang.train.business.resp.TrainSeatQueryResp;
import com.chang.train.business.service.TrainSeatService;
import com.chang.train.resp.CommonResp;
import com.chang.train.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-seat")
public class TrainSeatAdminController {

@Resource
private TrainSeatService trainSeatService;

@PostMapping("/save")
public CommonResp<Object> save(@Valid @RequestBody TrainSeatSaveReq req) {
    trainSeatService.save(req);
    return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainSeatQueryResp>> queryList(@Valid TrainSeatQueryReq req) {
        PageResp<TrainSeatQueryResp> list = trainSeatService.queryList(req);
            return new CommonResp<>(list);
            }

            @DeleteMapping("/delete/{id}")
            public CommonResp<Object> delete(@PathVariable Long id) {
                trainSeatService.delete(id);
                return new CommonResp<>();
                }

                }
