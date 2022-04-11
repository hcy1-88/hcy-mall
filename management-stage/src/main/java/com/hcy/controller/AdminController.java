package com.hcy.controller;


import com.hcy.dto.GoodsForAdmin;
import com.hcy.dto.LoginDto;
import com.hcy.entity.Admin;
import com.hcy.feign.GoodsFeign;
import com.hcy.pojo.R;
import com.hcy.service.AdminService;
import com.hcy.util.Base64Util;
import com.hcy.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public R register(@RequestBody Admin admin){
        String raw = Base64Util.decode(admin.getPassword());  // 解密后的信息
//        System.out.println(raw);
        admin.setPassword(raw);
        return adminService.addAdmin(admin);
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginDto loginDto){
        // 解密
        String s = Base64Util.decode(loginDto.getPassword());
        loginDto.setPassword(s);
        return adminService.login(loginDto.getEmail(), loginDto.getPassword());
    }

    @GetMapping("/checkGoods")
    public R checkGoods(){
        return adminService.findAllForCheck();
    }

    @GetMapping("/modifyStatus")
    public R modifyStatus(@RequestParam("skuId") Integer skuId,@RequestParam("status") Integer status){
        return adminService.modifyCheck(skuId, status);
    }
}

