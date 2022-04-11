package com.hcy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcy.entity.Coupon;
import com.hcy.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcy
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @RequestMapping("/getAllCoupon")
    public List<Coupon> getAllCoupon() {
        QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enable", 1);
        return couponService.list(queryWrapper);
    }

    @RequestMapping("/updateCoupon")
    public String updateCoupon(@RequestParam("coupon") @RequestBody Coupon coupon) {
        this.couponService.updateById(coupon);
        return "ok";
    }
}

