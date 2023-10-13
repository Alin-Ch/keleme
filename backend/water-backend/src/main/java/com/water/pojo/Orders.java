package com.water.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/14/23:45
 * @Description:       TODO:订单实体类
 */
@Data
@Table(name = "orders")
public class Orders {

    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    @JsonSerialize(using = ToStringSerializer.class)    //由于使用雪花算法得到的id在前端会丢失精度，故使用@JsonSerialize将其变成json字符串
    private Long orderid;

    //订单号
    //OneToMany标识一对多外键，由于使用雪花算法得到的id在前端会丢失精度，故使用@JsonSerialize将其变成json字符串
    @OneToMany(mappedBy = "order_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ordernum;

    //订单状态
    @Column(name = "statu")
    private Integer statu;

    //实收金额
    @Column(name = "money")
    private Double money;

    //订单创建时间
    @Column(name = "createtime")
    private String createtime;

    //下单用户id
    @Column(name = "order_userid")
    private Long order_userid;

    //地址id
    @Column(name = "address_id")
    private Long address_id;

    //订单备注
    @Column(name = "remark")
    private String remark;

    @Transient
    private String userName;

    @Transient
    private String addressName;

    @Transient
    private String orderUName;

    @Transient
    private String Phone;

}











