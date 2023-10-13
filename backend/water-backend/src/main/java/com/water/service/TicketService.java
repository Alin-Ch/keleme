package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Params;
import com.water.pojo.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/12/16:55
 * @Description:
 */
public interface TicketService {
    /**
     * 查询所有
     * @return
     */
    List<Ticket> findAll();

    /**
     * 查询和分页查询水票
     * @param params
     * @return
     */
    PageInfo<Ticket> findBySearch(@Param("params") Params params);

    /**
     * 新增水票
     * @param ticket
     */
    void add(Ticket ticket);

    /**
     * 更新水票
     * @param ticket
     */
    void update(Ticket ticket);

    /**
     * 根据id删除水票
     * @param tid
     */
    void delete(Integer tid);
}
