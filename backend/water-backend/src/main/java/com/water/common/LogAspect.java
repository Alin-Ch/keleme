package com.water.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.water.pojo.Log;
import com.water.pojo.User;
import com.water.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/17/14:58
 * @Description:        TODO:AOP（@Around）环绕增强实现日志记录
 */
@Component
@Aspect
public class LogAspect {

    @Resource
    private LogService logService;

    /**
     * TODO：环绕增强，目标方法执行前后执行，如果异常，环绕后的方法不执行
     * @param joinPoint
     * @param autoLog
     * @return
     * @throws Throwable
     */
    @Around("@annotation(autoLog)")
    public Object doAround(ProceedingJoinPoint joinPoint,AutoLog autoLog) throws Throwable {

        //TODO:操作内容，我们在注解里已经定义了value(),在需要切入的接口上面去写上对应的操作内容即可
        String name = autoLog.value();
        //TODO:记录操作时间（当前时间）
        String time = DateUtil.now();

        //TODO:操作人
        String username = "";
        User user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNotNull(user)){
            username = user.getUsername();
        }

        //TODO:操作人IP（也就是之前jwt获取当前登录用户的信息）
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //TODO:获取主机地址
        String ip = request.getRemoteAddr();


        //TODO:传递参数为连接点对象执行具体的接口，joinPoint表示任何通知者都可用，可以获得当前目标对象，目标方法参数等
        R r = (R) joinPoint.proceed();

        //TODO:获取目标对象，将当前用户拿到
        Object data = r.getData();
        if (data instanceof User){
            User user1 = (User) data;
            username = user1.getUsername();
        }

        //TODO:完成后往日志表里写一条记录
        // 在插入日志之前，为 id 字段设置一个唯一的值
        Log log = new Log(null,name,time,username,ip);
        logService.add(log);

        return r;
    }

}







