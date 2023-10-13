package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.common.JwtTokenUtils;
import com.water.exception.CustomException;
import com.water.mapper.UserMapper;
import com.water.pojo.Params;
import com.water.pojo.User;
import com.water.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/07/16:14
 * @Description:    TODO:业务层接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    /**
     * 查询所有
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

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
    public PageInfo<User> findBySearch(Params params) {
        //开启分页查询PageHelper自动进行、前提要开启分页功能
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<User> list = userMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增操作     TODO:保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param user
     */
    @Override
    public void add(User user) {

        //1.用户名一定要有，否则不让新增（后面需要用户名登录）
        if (user.getUsername() == null || "".equals(user.getUsername())){
            throw new CustomException("用户名不能为空");
        }
        //2.进行重复性判断，同一个名字的不允许重复新增：只要根据用户名去数据库查询一下就可以了
        User user1 = userMapper.findByName(user.getUsername());
        if (user1 != null){
            //已存在，这里提示前台不允许新增
            //全局异常处理
            throw new CustomException("该用户名已存在");
        }
        //初始化一个密码
        if (user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insertSelective(user);
    }

    /**
     * 修改/更新    TODO:根据主键更新属性不为null的值
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 根据id删除某条记录   TODO:根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param userid
     */
    @Override
    public void delete(Integer userid) {
        userMapper.deleteByPrimaryKey(userid);
    }

    /**
     * 登录接口实现
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        //1.进行一些非空判断
        //判断用户名(用户名为手机号)非空
        if (user.getPhone() == null || "".equals(user.getPhone())){
            throw new CustomException("用户名不能为空");
        }
        //判断密码非空
        if (user.getPassword() == null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空");
        }
        //2.从数据库里面根据这个用户名和密码去查询对应的用户信息
        User user2 = userMapper.findByNameAndPassword(user.getPhone(),user.getPassword());
        if (user2 == null){
            // 如果查出来没有，那说明输入的用户名或者密码有误，提示用户，不允许登录
            throw new CustomException("用户名或密码有误");
        }
        // 如果查出来了有，那说明确实有这个人，而且输入的用户名和密码都对
        //生成该登录用户对应的token，然后跟着user一起返回到前台
        String token = JwtTokenUtils.genToken(user2.getUserid().toString(), user2.getPassword());
        user2.setToken(token);
        return user2;
    }

    @Override
    public User findById(Integer userid) {
        return userMapper.selectByPrimaryKey(userid);
    }


}
