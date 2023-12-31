package com.chang.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.chang.train.context.LoginMemberContext;
import com.chang.train.member.domain.Passenger;
import com.chang.train.member.domain.PassengerExample;
import com.chang.train.member.mapper.PassengerMapper;
import com.chang.train.member.req.PassengerQueryReq;
import com.chang.train.member.req.PassengerSaveReq;
import com.chang.train.member.resp.PassengerQueryResp;
import com.chang.train.resp.PageResp;
import com.chang.train.util.SnowUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: PassengerService
 * @Author Chang
 * @Date: 2023-09-24 20:55
 * @Version 1.0
 */
@Service
public class PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    @Resource
    private PassengerMapper passengerMapper;

    // 后续界面操作时，保存后，界面会刷新列表，不需要返回保存成功后的数据。
    /**
     * 根据id是否为空，判断是新增保存，还是修改保存。
     * @param req
     */
    public void save(PassengerSaveReq req){
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        if (ObjectUtil.isNull(passenger.getId())) {
//            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            // todo 使用AOP来自动封装时间。
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            // 更新操作
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }


    /**
     * 查询所有的乘客，包括分页查询
     * @param req
     * @return
     */
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        passengerExample.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        // 分页是从1开始的。 对这句往下遇到的第一个sql做拦截，增加Limit操作。
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);

        // 自动生成一个select count 去查询条数。
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<PassengerQueryResp> list = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);

        PageResp<PassengerQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }

}
