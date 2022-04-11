package com.hcy.controller;

import com.hcy.entity.Address;
import com.hcy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/18 18:31
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping("/getAddressByUid")
    public List<Address> getAddressByUid(String userId) {
        return addressService.getAddressByUid(userId);
    }
}
