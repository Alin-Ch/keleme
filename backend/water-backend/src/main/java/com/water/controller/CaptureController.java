package com.water.controller;

import com.water.common.CaptureConfig;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/10/8:53
 * @Description:        TODO:图形验证码功能
 */
@CrossOrigin
@RestController
@RequestMapping
public class CaptureController {

    @GetMapping("/captcha")
    public void captcha(@RequestParam String key,HttpServletRequest request, HttpServletResponse response) throws IOException {
        //png类型
        //指定验证码的长宽以及字符的个数
        SpecCaptcha captcha = new SpecCaptcha(135,33,5);
        //数字和字母相结合
        captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
        //首先把验证码在后台保存一份，但是不能保存在session中，因为前后端分离后两个url不是同一个
        //方法：可以保存在redis中，也可以保存到Map中
        /**
         *TODO:每次在前端调用后台拿验证码时都要有一个key，来跟后台生成验证码后根据key来绑定，点击登录后把key带到后台去，
         * 后台生成的验证码与保存的key做比较，一致则登录成功，否则失败
         */
        CaptureConfig.CAPTURE_MAP.put(key,captcha.text().toLowerCase());
        CaptchaUtil.out(captcha,request,response);
    }
}












