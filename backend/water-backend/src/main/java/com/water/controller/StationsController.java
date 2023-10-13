package com.water.controller;

import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.pojo.Good;
import com.water.pojo.Params;
import com.water.pojo.Stations;
import com.water.service.GoodService;
import com.water.service.StationsService;
import com.water.utils.Calculating;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/11/17:01
 * @Description:
 */
@RestController
@RequestMapping("/station")
public class StationsController {

    @Resource
    private StationsService stationsService;

    @Resource
    private GoodService goodService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public R findAll(){
        List<Stations> list = stationsService.findAll();
        return R.success(list);
    }

    /**
     *分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Stations> info = stationsService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改
     * @param stations
     * @return
     */
    @PostMapping
    public R save(@RequestBody Stations stations){
        //判断用户id是否为空，为空则新增，否则为修改
        if (stations.getSid() == null){
            stationsService.add(stations);
        }else{
            stationsService.update(stations);
        }
        return R.success();
    }

    /**
     * 根据id删除水站
     * @param sid
     * @return
     */
    @DeleteMapping("/{sid}")
    public R delete(@PathVariable("sid") Integer sid){
        stationsService.delete(sid);
        return R.success();
    }

    /**
     * 获取在配送距离内的所有水站
     */
    @GetMapping("/suitable")
    public R getSuitable(Params params){
        String jingdu = params.getJingdu();
        String weidu = params.getWeidu();

        // 先将字符串形式的经纬度转换为double类型
        double inputWeidu = Double.parseDouble(weidu);
        double inputJingdu = Double.parseDouble(jingdu);

        //定义一个符合距离的水站列表
        List<Stations> suitableList = new ArrayList<>();

        // 遍历全部水站
        List<Stations> list = stationsService.findAll();

        //定义一个水站商品的集合，用于保存查询到的附近水站的商品
        List<Object> objectList = new ArrayList<>();

        //计算距离的工具类
        Calculating calculating = new Calculating();

        for (Stations station : list) {
            // 将字符串形式的经纬度转换为double类型
            double stationWeidu = Double.parseDouble(station.getWeidu());
            double stationJingdu = Double.parseDouble(station.getJingdu());

            // 根据两点经纬度计算距离
            double distance = calculating.getDistance(inputWeidu, inputJingdu, stationWeidu, stationJingdu);
            double distribution = Double.parseDouble(station.getDistribution());
            // 更新最小距离和最近站点名称
            if (distance < distribution) {
                suitableList.add(station);
            }
        }
        for (Stations stations : suitableList) {
            int sNum = stations.getSid();
            List<Good> stationGoods = goodService.findAllForStation(sNum);
            objectList.addAll(stationGoods);
        }
        // 返回距离最近的站点名称
        return R.success(objectList);
    }


}
