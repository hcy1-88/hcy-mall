package com.hcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.entity.Address;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/18 18:40
 */
public interface AddressService extends IService<Address> {
    List<Address> getAddressByUid(String userId);
}
