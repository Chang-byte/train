package com.chang.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.chang.train.member.domain.Passenger;
import com.chang.train.member.mapper.PassengerMapper;
import com.chang.train.member.req.PassengerSaveReq;
import com.chang.train.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @title: PassengerService
 * @Author Chang
 * @Date: 2023-09-24 20:55
 * @Version 1.0
 */
@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    // 后续界面操作时，保存后，界面会刷新列表，不需要返回保存成功后的数据。
    public void save(PassengerSaveReq req){
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        if (ObjectUtil.isNull(passenger.getId())) {
//            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }

}
