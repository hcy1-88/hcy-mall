package com.hcy.controller;


import com.hcy.dto.LoginDto;
import com.hcy.entity.Seller;
import com.hcy.pojo.R;
import com.hcy.service.SellerService;
import com.hcy.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Base64;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/register")
    private R register(@RequestBody Seller seller){
        String raw = Base64Util.decode(seller.getPassword());  // 解密后的信息
        seller.setPassword(raw);
        return sellerService.addSeller(seller);
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginDto loginDto){
        String raw = Base64Util.decode(loginDto.getPassword());  // 获取用户输入的密码
        loginDto.setPassword(raw);
        return sellerService.login(loginDto.getEmail(), loginDto.getPassword());
    }

}

