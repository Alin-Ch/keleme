package com.water.mapper;

import com.water.pojo.Employee;
import com.water.pojo.Params;
import com.water.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/12 012 15:57
 * @Description:
 */
@Repository
public interface EmployeeMapper extends Mapper<Employee> {

    List<Employee> findBySearch(@Param("params") Params params);

    @Select("select * from employee where empname = #{empname} limit 1;")
    Employee findByName(@Param("empname") String empname);

    @Select("select * from employee where phone = #{phone} and password = #{password} limit 1;")
    Employee findByPhoneAndPassword(@Param("phone") String phone, String password);

    @Select("select * from employee where phone = #{phone} and password = #{password} limit 1;")
    Employee findByNameAndPassword(String phone, String password);
}
