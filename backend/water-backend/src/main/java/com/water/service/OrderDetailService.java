package com.water.service;

import com.water.pojo.OrderDetails;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/21/9:03
 * @Description:
 */
public interface OrderDetailService {
    /**
     * 查询全部订单详情
     * @return
     */
    List<OrderDetails> findAll();

    /**
     * 新增订单时新增订单详情
     * @param details
     */
    void add(OrderDetails details);

    /**
     * 根据订单id查询订单详情
     * @param id
     * @return
     */
    List<OrderDetails> findById(Long id);
}
