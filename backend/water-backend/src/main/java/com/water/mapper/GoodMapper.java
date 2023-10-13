package com.water.mapper;

import com.water.pojo.Good;
import com.water.pojo.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/09/20:51
 * @Description:
 */
@Repository
public interface GoodMapper extends BaseMapper<Good> {
    /**
     * 数据访问层接口的方法
     * @param params
     * @return
     */
    List<Good> findBySearch(@Param("params") Params params);

    /**
     * 自定义绘制饼图时查询商品数据
     * @return
     */
    List<Good> findAllForGood();

    @Select("select * from good where station_id = #{sNum}")
    List<Good> findForStation(int sNum);
}












