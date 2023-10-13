package com.water.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.water.exception.CustomException;
import com.water.mapper.AddressMapper;
import com.water.pojo.Address;
import com.water.pojo.Params;
import com.water.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Alin
 * @date 2023/9/13 013 15:56
 * @Description:
 */
@Service
public class AddressServiceImpl implements AddressService {


    @Resource
    private AddressMapper addressMapper;

    /**
     * 查询全部地址
     * @return
     */
    @Override
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }

    /**
     * 查询/分页查询地址
     * @param params
     * @return
     */
    @Override
    public PageInfo<Address> findBySearch(Params params) {
        //开启分页查询PageHelper自动进行、前提要开启分页功能
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Address> list = addressMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增地址
     * @param address
     */
    @Override
    public void add(Address address) {
        //1.用户名一定要有，否则不让新增（后面需要用户名登录）
        if (address.getUsername() == null || "".equals(address.getUsername())){
            throw new CustomException("收货人名称不能为空");
        }
        addressMapper.insertSelective(address);
    }

    /**
     * 更新地址
     * @param address
     */
    @Override
    public void update(Address address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }

    /**
     * 根据id删除地址
     * @param addressid
     */
    @Override
    public void delete(Integer addressid) {
        addressMapper.deleteByPrimaryKey(addressid);
    }

    @Override
    public List<Address> getAddressById(Integer id) {
        return addressMapper.getAddressById(id);
    }

    @Override
    public Address getAddressByAddrid(Long id) {
        return addressMapper.selectByPrimaryKey(id);
    }

}
