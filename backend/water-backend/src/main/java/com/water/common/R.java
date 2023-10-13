package com.water.common;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/13:40
 * @Description:    TODO:封装统一返回数据
 */
@Data
public class R {

    private static final String SUCCESS = "200";
    private static final String ERROR = "-1";

    private String code;
    private String msg;
    private Object data;

    public static R success(){
        R r = new R();
        r.setCode(SUCCESS);
        return r;
    }

    /**
     * 往前台返回数据
     * @param data
     * @return
     */
    public static R success(Object data){
        R r = new R();
        r.setCode(SUCCESS);
        r.setData(data);
        return r;
    }

    /**
     * 返回失败的结果
     * @param msg
     * @return
     */
    public static R error(String msg){
        R r = new R();
        r.setCode(ERROR);
        r.setMsg(msg);
        return r;
    }
}













