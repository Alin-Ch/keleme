package com.water.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.mapper.LogMapper;
import com.water.pojo.Log;
import com.water.pojo.Params;
import com.water.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:54
 * @Description:
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    /**
     * 新增日志
     * @param log
     */
    @Override
    public void add(Log log) {
        logMapper.insertSelective(log);
    }

    /**
     * 查询日志/分页查询
     * @param params
     * @return
     */
    @Override
    public PageInfo<Log> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Log> list = logMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 删除日志
     * @param id
     */
    @Override
    public void delete(Integer id) {
        logMapper.deleteByPrimaryKey(id);
    }

}













