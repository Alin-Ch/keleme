package com.water.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/06/23:45
 * @Description:
 */
@Data
@Table(name = "user")
public class User {
    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Long userid;

    //用户名
    @Column(name = "username")
    private String username;

    //密码
    @Column(name = "password")
    private String password;

    //电话
    @Column(name = "phone")
    private String phone;

    //性别
    @Column(name = "gender")
    private String gender;

    //头像
    @Column(name = "avatar")
    private String avatar;

    //角色
    @Column(name = "role")
    private String role;

    //积分
    @Column(name = "jifen")
    private Integer jifen;

    //令牌
    @Transient
    private String token;

    //登录验证码
    @Transient
    private String verCode;

}














