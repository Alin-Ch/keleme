package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Employee;
import com.water.pojo.Params;
import com.water.pojo.User;

import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/12 012 15:56
 * @Description:
 */
public interface EmployeeService {
    /**
     * 查询所有
     * @return
     */
    List<Employee> findAll();

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageInfo<Employee> findBySearch(Params params);

    /**
     * 新增
     * @param employee
     */
    void add(Employee employee);

    /**
     * 更新
     * @param employee
     */
    void update(Employee employee);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 员工登录
     * @param employee
     * @return
     */
    Employee login(Employee employee);
}
