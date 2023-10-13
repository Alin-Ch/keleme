package com.water.controller;

import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.pojo.Address;
import com.water.pojo.Params;
import com.water.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/13 013 15:53
 * @Description:
 */
@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {
    @Resource
    private AddressService addressService;

    /**
     * 查询全部地址
     * @return
     */
    @GetMapping
    public R findAll(){
        List<Address> list = addressService.findAll();
        return R.success(list);
    }

    /**
     *分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Address> info = addressService.findBySearch(params);
        return R.success(info);
    }


    /**
     * 新增和修改
     * @param address
     * @return
     */
    @PostMapping
    public R save(@RequestBody Address address){
        //判断用户id是否为空，为空则新增，否则为修改
        if (address.getAddressid() == null){
            addressService.add(address);
        }else{
            addressService.update(address);
        }
        return R.success();
    }

    /**
     * 根据address_userid查询用户地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findAddressById(@PathVariable("id") Integer id){
        List<Address> list = addressService.getAddressById(id);
        return R.success(list);
    }
    /**
     * 根据id删除某条记录
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer id){
        addressService.delete(id);
        return R.success();
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/search/{id}")
    public R searchAddressById(@PathVariable("id") Long id){
        Address address = addressService.getAddressByAddrid(id);
        return R.success(address);
    }
}






