package com.water.controller;

import com.github.pagehelper.PageInfo;
import com.water.common.AutoLog;
import com.water.common.CaptureConfig;
import com.water.common.R;
import com.water.pojo.Employee;
import com.water.pojo.Params;
import com.water.pojo.User;
import com.water.service.EmployeeService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/12 012 15:48
 * @Description:    TODO:员工控制层
 */
@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public R findAll(){
        List<Employee> list = employeeService.findAll();
        return R.success(list);
    }

    /**
     *分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Employee> info = employeeService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改，根据用户id判断，id为空则新增，否则为修改
     * @param employee
     * @return
     */
    @PostMapping
    public R save(@RequestBody Employee employee){
        //判断用户id是否为空，为空则新增，否则为修改
        if (employee.getEmpid() == null){
            employeeService.add(employee);
        }else{
            employeeService.update(employee);
        }
        return R.success();
    }

    /**
     * 根据id删除某条记录
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer id){
        employeeService.delete(id);
        return R.success();
    }

    @PostMapping("/login")
    @AutoLog("登录该系统")
    public R login(@RequestBody Employee employee, @RequestParam String key, HttpServletRequest request){
        //判断验证码对不对
        if (!employee.getVerCode().toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))){
            //不相等，则验证不通过则清除输入的验证码
            CaptchaUtil.clear(request);
            return R.error("验证码输入错误");
        }
        Employee loginUser = employeeService.login(employee);
        //将自定义的map中的也清除
        CaptureConfig.CAPTURE_MAP.remove(key);
        return R.success(loginUser);
    }


}
