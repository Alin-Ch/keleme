package com.water.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/08/15:04
 * @Description:
 */
@Data
public class Params {
    private String name;
    private String phone;

    private String someoneName;

    //分页查询的参数
    private Integer pageNum;     //当前页
    private Integer pageSize;    //每页展示条数

    //经纬度参数
    private String weidu;
    private String jingdu;

    //订单所需参数
    private Integer userid;
    private Address address;
    private Orders orders;
    List<OrderItem> goodList;

}





