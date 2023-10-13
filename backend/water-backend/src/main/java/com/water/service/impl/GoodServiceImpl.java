package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.mapper.GoodMapper;
import com.water.pojo.Good;
import com.water.pojo.Params;
import com.water.service.GoodService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/09/20:53
 * @Description:
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Resource
    private GoodMapper goodMapper;


    /**
     * 查询所有商品/分页查询
     * @param params
     * @return
     */
    @Override
    public PageInfo<Good> findBySearch(Params params) {
        //开启分页查询PageHelper自动进行、前提要开启分页功能
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Good> list = goodMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增商品
     * @param good
     */
    @Override
    public void add(Good good) {
        goodMapper.insertSelective(good);
    }

    /**
     * 更新商品
     * @param good
     */
    @Override
    public void update(Good good) {
        goodMapper.updateByPrimaryKeySelective(good);
    }

    /**
     * 根据id删除
     * @param goodid
     */
    @Override
    public void delete(Integer goodid) {
        goodMapper.deleteByPrimaryKey(goodid);
    }

    /**
     * 绘制饼图查询所有商品数据
     * @return
     */
    @Override
    public List<Good> findAllForGood() {
        return goodMapper.findAllForGood();
    }

    /**
     * 根据得到的水站id查询该水站的所有商品
     * @param sNum
     * @return
     */
    @Override
    public List<Good> findAllForStation(int sNum) {
        return goodMapper.findForStation(sNum);
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @Override
    public Good findById(Integer id) {
        return goodMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询全部商品
     * @return
     */
    @Override
    public List<Good> findAll() {
        return goodMapper.selectAll();
    }

}








