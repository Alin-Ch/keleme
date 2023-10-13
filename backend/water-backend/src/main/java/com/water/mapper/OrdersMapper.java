package com.water.mapper;

import com.water.pojo.Orders;
import com.water.pojo.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/14/23:24
 * @Description:
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 查询和分页查询所有订单
     * @param params
     * @return
     */
    List<Orders> findBySearch(@Param("params") Params params);

    /**
     * 根据当前的登陆用户id查询他自己的订单
     * @param id
     * @return
     */
    List<Orders> findById(Long id);

    /**
     * 更新订单的状态
     * @param existingOrder
     */
    void updateForStatus(Orders existingOrder);
}
