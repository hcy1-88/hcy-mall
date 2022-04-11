package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.mapper.AddressMapper;
import com.hcy.service.AddressService;
import org.springframework.stereotype.Service;
import com.hcy.entity.Address;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/18 18:42
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper,Address> implements AddressService {
    @Override
    public List<Address> getAddressByUid(String userId) {
        QueryWrapper<Address> aqw = new QueryWrapper<>();
        aqw.eq("user_id",userId);
        return this.baseMapper.selectList(aqw);
    }
}
