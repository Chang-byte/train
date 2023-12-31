package com.chang.train.business.controller.admin;

import com.chang.train.business.req.StationQueryReq;
import com.chang.train.business.req.StationSaveReq;
import com.chang.train.business.resp.StationQueryResp;
import com.chang.train.business.service.StationService;
import com.chang.train.business.service.TrainSeatService;
import com.chang.train.resp.CommonResp;
import com.chang.train.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 针对管理控制台的请求，我们使用admin开头，也跳过了 拦截器的部分。
// 接口隔离，不同的权限请求。
@RequestMapping("/admin/station")
public class StationAdminController {

    @Resource
    private StationService stationService;

    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody StationSaveReq req) {
        stationService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<StationQueryResp>> queryList(@Valid StationQueryReq req) {
        PageResp<StationQueryResp> list = stationService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        stationService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryList() {
        List<StationQueryResp> list = stationService.queryAll();
        return new CommonResp<>(list);
    }


    /**
     * 生成一辆火车的全部的座位；
     * 一定要考虑重复生成的问题。
     * 重复生成的两种做法：
     *  1. 存在就跳过
     *  2. 先删除已有的，再进行插入。
     *
     * @param trainCode
     * @return
     */
    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp<Object> genSeat(@PathVariable String trainCode) {
        trainSeatService.genTrainSeat(trainCode);
        return new CommonResp<>();
    }
}
