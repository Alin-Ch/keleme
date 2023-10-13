package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Orders;
import com.water.pojo.Params;

import java.util.List;


/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/14/23:25
 * @Description:
 */
public interface OrderService {
    /**
     * 查询所有订单
     * @return
     */
    List<Orders> findAll();

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageInfo<Orders> findBySearch(Params params);

    /**
     * 新增订单
     * @param orders
     */
    void add(Orders orders);

    /**
     * 修改订单
     * @param orders
     */
    void update(Orders orders);

    /**
     * 删除订单
     * @param id
     */
    void delete(Long id);

    /**
     * 根据当前的登陆用户id查询他自己的订单
     * @param id
     * @return
     */
    List<Orders> findById(Long id);

    Orders findOrderById(Long id);

    /**
     * 修改订单
     * @param existingOrder
     */
    void updateForOrder(Orders existingOrder);
}
