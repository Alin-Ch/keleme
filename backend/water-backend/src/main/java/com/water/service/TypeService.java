package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Params;
import com.water.pojo.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/10/15:44
 * @Description:
 */
public interface TypeService {
    /**
     * 新增分类
     * @param type
     */
    void add(Type type);

    /**
     * 更新分类
     * @param type
     */
    void update(Type type);

    /**
     * 分页查询/查询全部/模糊查询
     * @param params
     * @return
     */
    PageInfo<Type> findBySearch(@Param("params") Params params);

    /**
     * 根据分类id删除该分类
     * @param typeId
     */
    void delete(Integer typeId);

    /**
     * 查询全部把商品的分类也查询出来
     * @return
     */
    List<Type> findAll();

}
