package com.water.mapper;

import com.water.pojo.Params;
import com.water.pojo.Type;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/10/15:42
 * @Description:
 */
@Repository
public interface TypeMapper extends BaseMapper<Type> {

    List<Type> findBySearch(@Param("params") Params params);
}
