package com.water.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.water.exception.CustomException;
import com.water.pojo.User;
import com.water.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/30/19:05
 * @Description:    TODO:jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.从http请求的header中获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)){
            //如果没拿到，再去参数里面拿一拿试试
            token = request.getParameter("token");
        }

        //2.开始执行认证，如果真的没有拿到就让用户重新登录
        if (StrUtil.isBlank(token)){
            throw new CustomException("无token，请重新登录");
        }
        //获取token中的adminId
        String userId;
        User admin;


        try {
            userId = JWT.decode(token).getAudience().get(0);
            //根据token中的userId查询数据库
            admin = userService.findById(Integer.parseInt(userId));
        } catch (Exception e) {
            String errMsg = "token验证失败，请重新登录";
            log.error(errMsg + ", token=" + token,e);
            throw new CustomException(errMsg);
        }

        if (admin == null){
            throw new CustomException("用户不存在，请重新登录");
        }

        try {
            //用户密码加签名验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token);//验证token
        } catch (JWTVerificationException e) {
            throw new CustomException("token验证失败，请重新登录");
        }
        log.info("token验证成功，允许放行");
        return true;
    }
}













