package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Params;
import com.water.pojo.Stations;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/11/17:00
 * @Description:
 */
public interface StationsService {
    /**
     * 分页查询
     * @param params
     * @return
     */
    PageInfo<Stations> findBySearch(Params params);

    /**
     * 查询全部
     * @return
     */
    List<Stations> findAll();

    /**
     * 新增水站
     * @param stations
     */
    void add(Stations stations);

    /**
     * 更新水站信息
     * @param stations
     */
    void update(Stations stations);

    /**
     * 根据水站id删除水站
     * @param sid
     */
    void delete(Integer sid);
}
