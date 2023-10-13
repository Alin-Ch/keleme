package com.water.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/21/8:55
 * @Description:    TODO:订单明细实体类
 */
@Data
@Table(name = "order_detail")
public class OrderDetails {

    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Long id;

    //商品名字
    @Column(name = "name")
    private String name;

    //订单id
    //标识外键多对一，对应orderid
    @ManyToOne
    @JoinColumn(name = "orderid")
    private Long order_id;

    //商品id
    @Column(name = "good_id")
    private Integer good_id;

    //商品数量
    @Column(name = "number")
    private Integer number;

    //金额
    @Column(name = "price")
    private Double price;

    @Transient
    private Double newPrice;

    @Transient
    private String orderUserName;

    @Transient
    private String addressName;

    @Transient
    private String phone;

}












