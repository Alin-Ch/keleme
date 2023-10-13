package com.water.service;


import com.github.pagehelper.PageInfo;
import com.water.pojo.Log;
import com.water.pojo.Params;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:53
 * @Description:
 */
public interface LogService {

    void add(Log log);

    PageInfo<Log> findBySearch(Params params);

    void delete(Integer id);

}
