package com.water.utils;


/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/20/16:47
 * @Description:
 */
public class Calculating {

    // 根据经纬度计算距离的方法，可以使用 Haversine Formula 等算法
    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        // 距离计算的具体实现由你自行完成，这里提供一个示例
        double radius = 6371; // 地球半径，单位为公里
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius * c;

        return distance*1000;
    }

}
