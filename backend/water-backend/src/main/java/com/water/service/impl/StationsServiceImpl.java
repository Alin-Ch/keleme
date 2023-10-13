package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.mapper.StationsMapper;
import com.water.pojo.Params;
import com.water.pojo.Stations;
import com.water.service.StationsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/11/17:01
 * @Description:
 */
@Service
public class StationsServiceImpl implements StationsService {

    @Resource
    private StationsMapper stationsMapper;

    /**
     * 模糊查询、按条件查询、分页查询
     * TODO:开始分页
     * Params:
     * pageNum – 页码
     * pageSize – 每页显示数量
     * @param params
     * @return
     */
    @Override
    public PageInfo<Stations> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Stations> list = stationsMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 查询全部水站
     * @return
     */
    @Override
    public List<Stations> findAll() {
        return stationsMapper.selectAll();
    }

    /**
     * 新增
     * @param stations
     */
    @Override
    public void add(Stations stations) {
        stationsMapper.insertSelective(stations);
    }

    /**
     * 更新水站信息
     * @param stations
     */
    @Override
    public void update(Stations stations) {
        stationsMapper.updateByPrimaryKeySelective(stations);
    }

    /**
     * 根据id删除水站
     * @param sid
     */
    @Override
    public void delete(Integer sid) {
        stationsMapper.deleteByPrimaryKey(sid);
    }

}









