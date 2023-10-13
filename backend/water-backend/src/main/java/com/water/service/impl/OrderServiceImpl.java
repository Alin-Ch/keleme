package com.water.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.mapper.OrdersMapper;
import com.water.pojo.Orders;
import com.water.pojo.Params;
import com.water.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/14/23:26
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrdersMapper ordersMapper;

    /**
     * 查询所有订单
     * @return
     */
    @Override
    public List<Orders> findAll() {
        return ordersMapper.selectAll();
    }

    /**
     * 查询和分页查询
     * @param params
     * @return
     */
    @Override
    public PageInfo<Orders> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Orders> list = ordersMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增的订单
     * @param orders
     */
    @Override
    public void add(Orders orders) {
        ordersMapper.insertSelective(orders);
    }

    /**
     * 更新/修改订单（如订单备注、订单状态）
     * @param orders
     */
    @Override
    public void update(Orders orders) {
        ordersMapper.updateByPrimaryKeySelective(orders);
    }

    /**
     * 删除订单（订单完成后）
     * @param id
     */
    @Override
    public void delete(Long id) {
        ordersMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查询所有订单
     * @param id
     * @return
     */
    @Override
    public List<Orders> findById(Long id) {
        return ordersMapper.findById(id);
    }

    /**
     * 根据id查询单个订单
     * @param id
     * @return
     */
    @Override
    public Orders findOrderById(Long id) {
        return ordersMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     * @param existingOrder
     */
    @Override
    public void updateForOrder(Orders existingOrder) {
        ordersMapper.updateForStatus(existingOrder);
    }


}
