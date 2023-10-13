package com.water.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author Alin
 * @date 2023/9/12 012 15:50
 * @Description:
 */
@Data
@Table(name = "employee")
public class Employee {
    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Long empid;

    //员工头像
    @Column(name = "empavatar")
    private String empavatar;

    //员工名字
    @Column(name = "empname")
    private String empname;

    //员工手机号
    @Column(name = "phone")
    private String phone;

    //员工手机号
    @Column(name = "password")
    private String password;

    //员工性别
    @Column(name = "gender")
    private String gender;

    //员工年龄
    @Column(name = "age")
    private Integer age;

    //员工所属水站id
    @Column(name = "station_id")
    private Integer station_id;

    @Transient
    private String stationName;

    //令牌
    @Transient
    private String token;

    //登录验证码
    @Transient
    private String verCode;

}
