package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.common.JwtTokenUtils;
import com.water.exception.CustomException;
import com.water.mapper.EmployeeMapper;
import com.water.pojo.Employee;
import com.water.pojo.Params;
import com.water.pojo.User;
import com.water.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/12 012 15:54
 * @Description:
 */
@Service
public class EmpServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> findAll() {
        return employeeMapper.selectAll();
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
    public PageInfo<Employee> findBySearch(Params params) {
        //开启分页查询PageHelper自动进行、前提要开启分页功能
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Employee> list = employeeMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增操作     TODO:保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param employee
     */
    @Override
    public void add(Employee employee) {
        //1.用户名一定要有，否则不让新增（后面需要用户名登录）
        if (employee.getEmpname() == null || "".equals(employee.getEmpname())){
            throw new CustomException("员工名不能为空");
        }
        //2.进行重复性判断，同一个名字的不允许重复新增：只要根据用户名去数据库查询一下就可以了
        Employee employee1 = employeeMapper.findByName(employee.getEmpname());
        if (employee1 != null){
            //已存在，这里提示前台不允许新增
            //全局异常处理
            throw new CustomException("该用户名已存在");
        }
        //初始化一个密码
        if (employee.getPassword() == null){
            employee.setPassword("123456");
        }
        employeeMapper.insertSelective(employee);
    }


    /**
     * 修改/更新    TODO:根据主键更新属性不为null的值
     * @param employee
     */
    @Override
    public void update(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public void delete(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Employee login(Employee employee) {
        //1.进行一些非空判断
        //判断用户名(用户名为手机号)非空
        if (employee.getPhone() == null || "".equals(employee.getPhone())){
            throw new CustomException("用户名不能为空");
        }
        //判断密码非空
        if (employee.getPassword() == null || "".equals(employee.getPassword())){
            throw new CustomException("密码不能为空");
        }
        //2.从数据库里面根据这个用户名和密码去查询对应的用户信息
        Employee employee2 = employeeMapper.findByNameAndPassword(employee.getPhone(),employee.getPassword());
        if (employee2 == null){
            // 如果查出来没有，那说明输入的用户名或者密码有误，提示用户，不允许登录
            throw new CustomException("用户名或密码有误");
        }
        // 如果查出来了有，那说明确实有这个人，而且输入的用户名和密码都对
        //生成该登录用户对应的token，然后跟着user一起返回到前台
        String token = JwtTokenUtils.genToken(employee2.getEmpid().toString(), employee2.getPassword());
        employee2.setToken(token);
        return employee2;
    }

}
