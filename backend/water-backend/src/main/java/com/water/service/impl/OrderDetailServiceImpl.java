package com.water.service.impl;

import com.water.mapper.OrderDetailMapper;
import com.water.pojo.OrderDetails;
import com.water.service.OrderDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/21/9:03
 * @Description:
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Resource
    private OrderDetailMapper detailMapper;

    /**
     * 查询全部订单明细
     * @return
     */
    @Override
    public List<OrderDetails> findAll() {
        return detailMapper.selectAll();
    }

    @Override
    public void add(OrderDetails details) {
        detailMapper.insertSelective(details);
    }

    @Override
    public List<OrderDetails> findById(Long id) {
        return detailMapper.selectById(id);
    }
}
