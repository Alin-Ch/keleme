package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Good;
import com.water.pojo.Params;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/09/20:53
 * @Description:
 */
public interface GoodService {
    /**
     * 查询全部/分页查询
     * @param params
     * @return
     */
    PageInfo<Good> findBySearch(Params params);

    /**
     * 新增
     * @param good
     */
    void add(Good good);

    /**
     * 更新
     * @param good
     */
    void update(Good good);

    /**
     * 根据id删除商品
     * @param goodid
     */
    void delete(Integer goodid);

    /**
     * 绘制饼图查询所有商品数据
     * @return
     */
    List<Good> findAllForGood();

    /**
     * 根据得到的水站id查询该水站的所有商品
     * @param sNum
     * @return
     */
    List<Good> findAllForStation(int sNum);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    Good findById(Integer id);

    /**
     * 查询全部商品
     * @return
     */
    List<Good> findAll();

}
