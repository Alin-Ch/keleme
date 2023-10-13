package com.water.controller;

import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.pojo.Params;
import com.water.pojo.Ticket;
import com.water.service.TicketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/12/16:56
 * @Description:    TODO:水票控制层
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    /**
     * 查询所有水票
     * @return
     */
    @GetMapping
    public R findAll(){
        List<Ticket> list = ticketService.findAll();
        return R.success(list);
    }

    /**
     * 查询和分页查询水票
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Ticket> info = ticketService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改水票
     * @param ticket
     * @return
     */
    @PostMapping
    public R save(@RequestBody Ticket ticket){
        //判断用户id是否为空，为空则新增，否则为修改
        if (ticket.getTicketid() == null){
            ticketService.add(ticket);
        }else{
            ticketService.update(ticket);
        }
        return R.success();
    }

    @DeleteMapping("/{tid}")
    public R delete(@PathVariable("tid") Integer tid){
        ticketService.delete(tid);
        return R.success();
    }


}











