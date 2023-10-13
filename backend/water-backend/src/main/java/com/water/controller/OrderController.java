package com.water.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.pojo.*;
import com.water.service.GoodService;
import com.water.service.OrderDetailService;
import com.water.service.OrderService;
import com.water.service.StationsService;
import com.water.utils.Calculating;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/14/23:26
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    //注入订单Bean
    @Resource
    private OrderService orderService;

    //注入订单明细Bean
    @Resource
    private OrderDetailService detailService;

    //注入水站Bean
    @Resource
    private StationsService stationsService;

    //注入商品Bean
    @Resource
    private GoodService goodService;

    /**
     * 查询全部订单
     *
     * @return
     */
    @GetMapping
    public R findAll() {
        List<Orders> list = orderService.findAll();
        return R.success(list);
    }

    /**
     * 根据当前的登陆用户id查询他自己的订单
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable("id") Long id) {
        List<Orders> list = orderService.findById(id);
        return R.success(list);
    }

    /**
     * 分页查询订单
     *
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params) {
        PageInfo<Orders> info = orderService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 根据订单id查询订单
     *
     * @param id
     * @return
     */
    @GetMapping("/search/{id}")
    public R searchAddressById(@PathVariable("id") Long id) {
        Orders orders = orderService.findOrderById(id);
        return R.success(orders);
    }

    /**
     * 修改订单
     *
     * @param orders
     * @return
     */
    @PostMapping
    public R save(@RequestBody Orders orders) {
        //判断id是否为空，为空则新增，否则为修改
        if (orders.getOrderid() != null) {
            Orders existingOrder = orderService.findOrderById(orders.getOrderid());
            if (orders.getStatu() == 1 || orders.getStatu() == 2 || orders.getStatu() == 3) {
                existingOrder.setStatu(orders.getStatu() + 1); // 更新statu字段
            }
            orderService.updateForOrder(existingOrder);
        }else{
            return R.error("订单不存在");
        }
        return R.success();
    }

    @PostMapping("/cancel")
    public R cancel(@RequestBody Orders orders) {
        //判断id是否为空，为空则新增，否则为修改
        if (orders.getOrderid() != null) {
            Orders existingOrder = orderService.findOrderById(orders.getOrderid());
            if (orders.getStatu() == 1 || orders.getStatu() == 2) {
                existingOrder.setStatu(0); // 更新statu字段，取消订单将状态修改为0
            }
            orderService.updateForOrder(existingOrder);
        }
        return R.success();
    }

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        orderService.delete(id);
        return R.success();
    }

    /**
     * 批量删除 PUT/delBatch
     *
     * @param list
     * @return
     */
    @PutMapping("/delBatch")
    public R delBatch(@RequestBody List<Orders> list) {
        for (Orders orders : list) {
            orderService.delete(orders.getOrderid());
        }
        return R.success();
    }


    /**
     * 根据前台传回的用户地址经纬度计算附近水站，并查询到附近这些水站的商品与用户所选的商品比较是否一致
     *
     * @param params
     * @return
     */
    @PostMapping("/create")
    public R save(@RequestBody Params params) {

        System.out.println(params);
        //这里获取小程序下单用户的地址，从地址里面拿经纬度
        String jingdu = params.getAddress().getJingdu();
        String weidu = params.getAddress().getWeidu();

        // 先将字符串形式的经纬度转换为double类型
        double inputWeidu = Double.parseDouble(weidu);
        double inputJingdu = Double.parseDouble(jingdu);

        //定义一个符合距离的水站列表
        List<Stations> suitableList = new ArrayList<>();

        // 遍历全部水站
        List<Stations> list = stationsService.findAll();

        //定义一个水站商品的集合，用于保存查询到的附近水站的所有商品
        List<Good> matchedGoods = new ArrayList<>();

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
        // 遍历符合距离要求的水站，查询水站的商品
        for (Stations stations : suitableList) {
            //获取水站的id
            int sNum = stations.getSid();
            //根据水站id查询商品
            List<Good> stationGoods = goodService.findAllForStation(sNum);
            //将查询出来的商品放进一个集合里面
            matchedGoods.addAll(stationGoods);
        }

        // 获取传入的商品列表
        List<OrderItem> inputGoods = params.getGoodList();

        // 存储匹配的商品ID
        Set<Integer> matchedIds = new HashSet<>();

        // 将传入的商品ID存入集合
        for (OrderItem good : inputGoods) {
            matchedIds.add(good.getGoodid());
        }

        // 比较匹配的商品ID是否存在于查询到的商品列表中
        List<Good> resultGoods = new ArrayList<>();
        for (Good good : matchedGoods) {
            if (matchedIds.contains(good.getGoodid())) {
                resultGoods.add(good);
            }
        }
        if (resultGoods.size() != inputGoods.size()) {
            return R.error("商品不在配送范围");
        }

        for (int i = 0; i < inputGoods.size(); i++) {
            int inputCount = inputGoods.get(i).getCount();
            int resultQuantity = 0;

            // 在 resultGoods 中找到匹配的 goodid 并获取其 quantity
            for (Good resultGood : resultGoods) {
                if (resultGood.getGoodid() == inputGoods.get(i).getGoodid()) {
                    resultQuantity = resultGood.getQuantity();
                    break;
                }
            }
            // 比较 count 和 quantity
            if (inputCount > resultQuantity) {
                return R.error(resultGoods.get(i).getGoodname() + "商品库存不足无法下单");
            }
        }

        // 创建Snowflake实例生成id
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        //加入同步锁，保证线程安全
        Long ordersId;
        synchronized (this) {
            Orders orders = new Orders();
            // 生成订单
            Long orderId = snowflake.nextId();
            ordersId = orderId;
            orders.setOrderid(orderId);
            orders.setOrdernum(orderId);
            orders.setMoney(params.getOrders().getMoney());
            orders.setCreatetime(DateUtil.now());
            orders.setOrder_userid(params.getAddress().getAddress_userid());
            orders.setAddress_id(params.getAddress().getAddressid());
            orders.setRemark(params.getOrders().getRemark());
            orderService.add(orders);

            //新增对应商品的订单详情
            for (int i = 0; i < resultGoods.size(); i++) {
                OrderDetails details = new OrderDetails();
                details.setName(resultGoods.get(i).getGoodname());
                details.setOrder_id(orderId);
                details.setGood_id(resultGoods.get(i).getGoodid());
                details.setNumber(params.getGoodList().get(i).getCount());
                details.setPrice(resultGoods.get(i).getNewprice() * params.getGoodList().get(i).getCount());

                Integer goodId = params.getGoodList().get(i).getGoodid();
                Good good = goodService.findById(goodId);
                if (good != null && goodId.equals(good.getGoodid())) {
                    // 商品 ID 匹配，可以进行库存扣减操作
                    int quantity = good.getQuantity();
                    int count = params.getGoodList().get(i).getCount();
                    if (quantity >= count) {
                        good.setQuantity(quantity - count);
                        good.setSold(good.getSold() + count);
                        goodService.update(good);
                    }
                }
                detailService.add(details);
            }
        }
        //R为统一返回的对象，返回订单编号
        return R.success(ordersId.toString());
    }


}







