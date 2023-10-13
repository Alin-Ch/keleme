package com.water.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/30/15:03
 * @Description:    TODO:配置拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //指定controller统一的接口前缀
        configurer.addPathPrefix("/api",clazz -> clazz.isAnnotationPresent(RestController.class));
    }

    //加自定义拦截器JwtInterceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/captcha")
                .excludePathPatterns("/api/type/upload")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/employee/login")
                .excludePathPatterns("/api/good/**")
                .excludePathPatterns("/api/address/**")


                .excludePathPatterns("/api/order/**")


                .excludePathPatterns("/api/user")


                .excludePathPatterns("/api/order/**")


                .excludePathPatterns("/api/orderDetail/**")


                .excludePathPatterns("/api/order/**")
                .excludePathPatterns("/api/orderDetail/**")

                .excludePathPatterns("/api/station")
                .excludePathPatterns("/api/station/suitable")
                .excludePathPatterns("/api/user/register");
    }


}
