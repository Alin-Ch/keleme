package com.water.controller;

import com.water.common.R;
import com.water.pojo.OrderDetails;
import com.water.service.OrderDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/21/13:13
 * @Description:
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Resource
    private OrderDetailService detailService;

    /**
     * 查询全部订单明细
     * @return
     */
    @GetMapping
    public R findAll(){
        List<OrderDetails> list =detailService.findAll();
        return R.success(list);
    }

    /**
     * 根据订单id查询这个订单的详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable("id") Long id){
        List<OrderDetails> list = detailService.findById(id);
        return R.success(list);
    }


}














