package com.water.pojo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/21/14:53
 * @Description:
 */
@Data
public class OrderItem {
    private Integer count;
    private Integer goodid;
    private Integer station_id;
}
