package com.water.pojo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/10/15:34
 * @Description:
 */
@Data
@Table(name = "type")
public class Type {
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Integer typeid;

    //分类名称
    @Alias("分类名称")      //@Alias 跟excel的表头做映射（批量上传时）
    @Column(name = "typeName")
    private String typeName;

    //分类描述
    @Alias("分类描述")      //@Alias 跟excel的表头做映射（批量上传时）
    @Column(name = "typeDescription")
    private String typeDescription;


}
