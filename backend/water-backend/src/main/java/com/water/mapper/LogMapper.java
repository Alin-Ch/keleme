package com.water.mapper;

import com.water.pojo.Log;
import com.water.pojo.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/20/13:33
 * @Description:
 */
@Repository
public interface LogMapper extends Mapper<Log> {

    List<Log> findBySearch(@Param("params") Params params);
}
