package com.water.mapper;

import com.water.pojo.Params;
import com.water.pojo.Ticket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/12/16:54
 * @Description:
 */
@Repository
public interface TicketMapper extends BaseMapper<Ticket> {

    /**
     * 查询和分页查询水票
     * @param params
     * @return
     */
    List<Ticket> findBySearch(@Param("params") Params params);
}
