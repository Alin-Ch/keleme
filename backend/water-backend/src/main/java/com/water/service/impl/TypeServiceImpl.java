package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.mapper.TypeMapper;
import com.water.pojo.Params;
import com.water.pojo.Type;
import com.water.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/10/15:44
 * @Description:    TODO:商品类型实现类
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    /**
     *新增分类
     * @param type
     */
    @Override
    public void add(Type type) {
        typeMapper.insertSelective(type);
    }

    /**
     * 更新/修改分类
     * @param type
     */
    @Override
    public void update(Type type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

    /**
     * 分页查询/查询全部/模糊查询分类
     * @param params
     * @return
     */
    @Override
    public PageInfo<Type> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Type> list = typeMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 根据id删除分类
     * @param typeId
     */
    @Override
    public void delete(Integer typeId) {
        typeMapper.deleteByPrimaryKey(typeId);
    }

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<Type> findAll() {
        return typeMapper.selectAll();
    }


}
