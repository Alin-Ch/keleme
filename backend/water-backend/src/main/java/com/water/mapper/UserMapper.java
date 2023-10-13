package com.water.mapper;

import com.water.pojo.Params;
import com.water.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/07/21:32
 * @Description:    TODO:该持久层操作的是user
 */
@Repository
public interface UserMapper extends Mapper<User> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<User> findBySearch(@Param("params") Params params);

    /**
     * 根据名字模糊查询
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username} limit 1;")
    User findByName(@Param("username") String username);

    /**
     * 根据手机号模糊查询
     * @param phone
     * @param password
     * @return
     */
    @Select("select * from user where phone = #{phone} and password = #{password} limit 1;")
    User findByNameAndPassword(@Param("phone") String phone, String password);


}










