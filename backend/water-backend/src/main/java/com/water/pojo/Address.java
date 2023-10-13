package com.water.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author Alin
 * @date 2023/9/13 013 15:47
 * @Description:
 */
@Data
@Table(name = "address")
public class Address {
    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Long addressid;

    //用户名
    @Column(name = "username")
    private String username;

    //收货手机号
    @Column(name = "phone")
    private String phone;

    //收货详细地址
    @Column(name = "detailaddress")
    private String detailaddress;

    //是否默认收货地址
    @Column(name = "isdefault")
    private String isdefault;

    //是否默认收货地址
    @Column(name = "address_userid")
    private Long address_userid;

    //收获地址的经度
    @Column(name = "jingdu")
    private String jingdu;

    //收获地址的纬度
    @Column(name = "weidu")
    private String weidu;

    @Transient
    private String name;

}
