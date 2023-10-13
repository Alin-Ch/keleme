package com.water.controller;

import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.pojo.Log;
import com.water.pojo.Params;
import com.water.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/20/13:41
 * @Description:
 */
@RestController
@RequestMapping("/log")
@CrossOrigin
public class LogController {

    @Resource
    private LogService logService;

    /**
     * 新增和修改
     * @param log
     * @return
     */
    @PostMapping
    public R save(@RequestBody Log log){
        logService.add(log);
        return R.success();
    }

    /**
     * 查询和分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Log> info = logService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 根据id删除日志
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer id){
        logService.delete(id);
        return R.success();
    }

    /**
     * 批量删除 PUT/delBatch
     * @param list
     * @return
     */
    @PutMapping("/delBatch")
    public R delBatch(@RequestBody List<Log> list){
        for (Log log : list) {
            logService.delete(log.getId());
        }
        return R.success();
    }
}
