package com.water.mapper;

import com.water.pojo.OrderDetails;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/21/9:02
 * @Description:
 */
@Repository
public interface OrderDetailMapper extends Mapper<OrderDetails> {
    /**
     * 根据订单id自定义查询订单详情
     * @param id
     * @return
     */
    List<OrderDetails> selectById(Long id);
}
