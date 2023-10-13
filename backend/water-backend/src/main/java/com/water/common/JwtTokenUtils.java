package com.water.common;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.water.pojo.User;
import com.water.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/30/16:40
 * @Description:    TODO:登录后所有的请求都携带Token以验证用户是否存在和是否为同一个
 */
@Component
public class JwtTokenUtils {
    private static UserServiceImpl staticUserService;
    //生成日志
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    public UserServiceImpl adminService;

    @PostConstruct
    public void setUserService() {
        staticUserService = adminService;
    }

    /**
     * TODO:生成token
     *
     * @param userId
     * @param password
     * @return
     */
    public static String genToken(String userId, String password) {
        return JWT.create().withAudience(userId)    //将userId 保存到token里面，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2h后token过期
                .sign(Algorithm.HMAC256(password));//签名以password作为token的密钥
    }

    /**
     * TODO:获取当前登录用户的信息
     *
     * @return
     */
    public static User getCurrentUser() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败，token:{}", token);
                return null;
            }
            //解析token，获取用户的id
            String adminId = JWT.decode(token).getAudience().get(0);
            return staticUserService.findById(Integer.valueOf(adminId));
        } catch (Exception e) {
            log.error("获取当前登录的token失败，token={}",token, e);
            return null;
        }
    }

}





















