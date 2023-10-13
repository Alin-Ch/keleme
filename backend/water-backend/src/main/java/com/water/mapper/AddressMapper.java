package com.water.mapper;

import com.water.pojo.Address;
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
 * @date 2023/9/13 013 15:57
 * @Description:
 */
@Repository
public interface AddressMapper extends Mapper<Address> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<Address> findBySearch(@Param("params") Params params);

    /**
     * 根据用户名模糊查询
     * @param username
     * @return
     */
    @Select("select * from address where username = #{username} limit 1;")
    Address findByName(@Param("username") String username);


    List<Address> getAddressById(Integer id);
}
