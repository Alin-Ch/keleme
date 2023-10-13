package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.mapper.TicketMapper;
import com.water.pojo.Params;
import com.water.pojo.Ticket;
import com.water.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/12/16:55
 * @Description:      TODO:水票业务层实现类
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Resource
    private TicketMapper ticketMapper;

    /**
     * 查询全部水票
     * @return
     */
    @Override
    public List<Ticket> findAll() {
        return ticketMapper.selectAll();
    }

    /**
     * 查询和分页查询水票
     * @param params
     * @return
     */
    @Override
    public PageInfo<Ticket> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Ticket> list = ticketMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增水票
     * @param ticket
     */
    @Override
    public void add(Ticket ticket) {
        ticketMapper.insertSelective(ticket);
    }

    /**
     * 更新水票
     * @param ticket
     */
    @Override
    public void update(Ticket ticket) {
        ticketMapper.updateByPrimaryKeySelective(ticket);
    }

    /**
     * 根据水票id删除水票
     * @param tid
     */
    @Override
    public void delete(Integer tid) {
        ticketMapper.deleteByPrimaryKey(tid);
    }
}








