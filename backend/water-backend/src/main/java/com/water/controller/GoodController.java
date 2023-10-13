package com.water.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.pojo.Good;
import com.water.pojo.Params;
import com.water.service.GoodService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/09/20:55
 * @Description:
 */
@CrossOrigin
@RestController
@RequestMapping("/good")
public class GoodController {

    @Resource
    private GoodService goodService;

    @GetMapping
    public R findAll(){
        List<Good> list = goodService.findAll();
        return R.success(list);
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable("id") Integer id){
        Good good = goodService.findById(id);
        return R.success(good);
    }

    /**
     *分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Good> info = goodService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改
     * @param good
     * @return
     */
    @PostMapping
    public R save(@RequestBody Good good){
        //判断用户id是否为空，为空则新增，否则为修改
        if (good.getGoodid() == null){
            goodService.add(good);
        }else{
            goodService.update(good);
        }
        return R.success();
    }

    /**
     * 根据商品id删除
     * @param goodid
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer goodid){
        goodService.delete(goodid);
        return R.success();
    }

    /**
     * 绘制饼图的接口查询所有商品
     * @return
     */
    @GetMapping("/echarts/bie")
    public R bie(){
        //查询所有商品
        List<Good> list= goodService.findAllForGood();
        //将其转换成流，然后过滤一遍（条件每一项里面的分类名称不能为空），分组整合key：分类名称，value：数量
        Map<String, Long> collect = list.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getTypeName()))
                .collect(Collectors.groupingBy(Good::getTypeName, Collectors.counting()));
        //最后返回给前台的数据结构,用list集合封装
        List<Map<String,Object>> mapList = new ArrayList<>();
        //整合之后的list不为空
        if (CollectionUtil.isNotEmpty(collect)){
            //遍历
            for (String key : collect.keySet()){
                Map<String,Object> map = new HashMap<>();
                map.put("name",key);
                map.put("value",collect.get(key));
                mapList.add(map);
            }
        }
        return R.success(mapList);
    }

    /**
     * 绘制柱状图
     * @return
     */
    @GetMapping("/echarts/bar")
    public R bar(){
        //查询所有商品
        List<Good> list= goodService.findAllForGood();
        //将其转换成流，然后过滤一遍（条件每一项里面的分类名称不能为空），分组整合key：分类名称，value：数量
        Map<String, Long> collect = list.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getTypeName()))
                .collect(Collectors.groupingBy(Good::getTypeName, Collectors.counting()));
        List<String> xAxis = new ArrayList<>();
        List<Long> yAxis = new ArrayList<>();
        //遍历
        if (CollectionUtil.isNotEmpty(collect)){
            for (String key : collect.keySet()){
                xAxis.add(key);
                yAxis.add(collect.get(key));
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("xAxis",xAxis);
        map.put("yAxis",yAxis);
        return R.success(map);
    }
}













