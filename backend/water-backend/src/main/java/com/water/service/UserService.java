package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Params;
import com.water.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/07/16:14
 * @Description:
 */
public interface UserService {
    /**
     * 查询所有
     * @return
     */
    List<User> findAll();

    /**
     * 分页查询、按条件查询
     * @param params
     * @return
     */
    PageInfo<User> findBySearch(Params params);

    /**
     * 新增操作
     * @param user
     */
    void add(User user);

    /**
     * 修改操作
     * @param user
     */
    void update(User user);

    /**
     * 根据删除某条数据
     * @param userid
     */
    void delete(Integer userid);

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 根据id查找单个用于jwt登录授权
     * @param userid
     * @return
     */
    User findById(Integer userid);


}
