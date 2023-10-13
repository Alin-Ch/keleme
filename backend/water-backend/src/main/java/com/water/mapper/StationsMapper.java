package com.water.mapper;


import com.water.pojo.Params;
import com.water.pojo.Stations;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/11/16:58
 * @Description:
 */
@Repository
public interface StationsMapper extends BaseMapper<Stations> {

    List<Stations> findBySearch(@Param("params") Params params);
}









