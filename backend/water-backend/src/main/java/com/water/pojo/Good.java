package com.water.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/09/20:43
 * @Description:
 */
@Data
@Table(name = "good")
public class Good {
    //主键id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键且自增
    private Integer goodid;

    //商品名称
    @Column(name = "goodname")
    private String goodname;

    //商品图片
    @Column(name = "cover")
    private String cover;

    //商品规格
    @Column(name = "size")
    private String size;

    //商品旧价格
    @Column(name = "oldprice")
    private Double oldprice;

    //商品新价格
    @Column(name = "newprice")
    private Double newprice;

    //商品类别
    @Column(name = "type_id")
    private Integer type_id;

    //水站id
    @Column(name = "station_id")
    private Integer station_id;

    //商品已售数量
    @Column(name = "sold")
    private Integer sold;

    //商品库存
    @Column(name = "quantity")
    private Integer quantity;

    //商品描述
    @Column(name = "description")
    private String description;

    //商品表和水站表、分类表的关联字段(用Transient标识为处理逻辑的字段)
    @Transient
    private String typeName;

    //商品表和水站表、分类表的关联字段(用Transient标识为处理逻辑的字段)
    @Transient
    private String stationsName;

}














