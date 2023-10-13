package com.water.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/12/16:47
 * @Description:    TODO:水票实体类
 */
@Data
@Table(name = "ticket")
public class Ticket {

    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Integer ticketid;

    //水票名称
    @Column(name = "ticketname")
    private String ticketname;

    //剩余使用次数
    @Column(name = "usecount")
    private Integer usecount;

    //水票价格
    @Column(name = "money")
    private String money;

    //水站id
    @Column(name = "t_stationid")
    private Integer t_stationid;

    //用户id
    @Column(name = "t_userid")
    private Integer t_userid;

    //商品表和水站表、分类表的关联字段(用Transient标识为处理逻辑的字段)
    @Transient
    private String userName;

    //商品表和水站表、分类表的关联字段(用Transient标识为处理逻辑的字段)
    @Transient
    private String stationsName;

}








