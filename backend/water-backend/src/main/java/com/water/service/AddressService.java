package com.water.service;

import com.github.pagehelper.PageInfo;
import com.water.pojo.Address;
import com.water.pojo.Params;
import com.water.pojo.User;

import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/13 013 15:54
 * @Description:
 */
public interface AddressService {

    /**
     * 查询全部地址
     * @return
     */
    List<Address> findAll();

    /**
     * 查询/分页查询地址
     * @param params
     * @return
     */
    PageInfo<Address> findBySearch(Params params);

    /**
     * 新增地址
     * @param address
     */
    void add(Address address);

    /**
     * 更新地址
     * @param address
     */
    void update(Address address);

    /**
     * 根据id删除地址
     * @param addressid
     */
    void delete(Integer addressid);

    /**
     * 根据address_userid查询用户地址
     * @param id
     * @return
     */
    List<Address> getAddressById(Integer id);

    Address getAddressByAddrid(Long id);
}


