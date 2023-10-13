package com.water.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/11/16:52
 * @Description:
 */
@Data
@Table(name = "stations")
public class Stations {
    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Integer sid;

    //水站名称
    @Column(name = "sname")
    private String sname;

    //水站地址
    @Column(name = "address")
    private String address;

    //负责人
    @Column(name = "manager")
    private String manager;

    //负责人电话
    @Column(name = "telephone")
    private String telephone;

    //纬度
    @Column(name = "weidu")
    private String weidu;

    //经度
    @Column(name = "jingdu")
    private String jingdu;

    //配送距离
    @Column(name = "distribution")
    private String distribution;

}















