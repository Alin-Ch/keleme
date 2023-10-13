package com.water.controller;

import com.github.pagehelper.PageInfo;
import com.water.common.AutoLog;
import com.water.common.CaptureConfig;
import com.water.common.R;
import com.water.pojo.Params;
import com.water.pojo.User;
import com.water.service.UserService;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/08/10:36
 * @Description:
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    //打印日志
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 登录+验证码
     * @param user
     * @return
     */
    @PostMapping("/login")
    @AutoLog("登录该系统")
    public R login(@RequestBody User user, @RequestParam String key, HttpServletRequest request){
        //判断验证码对不对
        if (!user.getVerCode().toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))){
            //不相等，则验证不通过则清除输入的验证码
            CaptchaUtil.clear(request);
            return R.error("验证码输入错误");
        }
        User loginUser = userService.login(user);
        //将自定义的map中的也清除
        CaptureConfig.CAPTURE_MAP.remove(key);
        return R.success(loginUser);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody User user){
        //注册其实就是新增，共用一个方法
        userService.add(user);
        return R.success();
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public R findAll(){
        List<User> list = userService.findAll();
        return R.success(list);
    }

    /**
     *分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        log.info("拦截器已放行，正式调用接口内部");
        PageInfo<User> info = userService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改，根据用户id判断，id为空则新增，否则为修改
     * @param user
     * @return
     */
    @PostMapping
    @AutoLog("修改用户信息")
    public R save(@RequestBody User user){
        //判断用户id是否为空，为空则新增，否则为修改
        if (user.getUserid() == null){
            userService.add(user);
        }else{
            userService.update(user);
        }
        return R.success();
    }

    /**
     * 根据id删除某条记录
     * @param userid
     * @return
     */
    @DeleteMapping("/{id}")
    @AutoLog("删除用户信息")
    public R delete(@PathVariable("id") Integer userid){
        userService.delete(userid);
        return R.success();
    }
}












