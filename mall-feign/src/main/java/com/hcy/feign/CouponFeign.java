package com.hcy.feign;

import com.hcy.entity.Coupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/5 2:03
 */
@FeignClient(contextId = "couponFeign", name = "goods-service",path = "/coupon")
public interface CouponFeign {
    @RequestMapping("/getAllCoupon") List<Coupon> getAllCoupon();
    @RequestMapping("/updateCoupon") String updateCoupon(@RequestParam("coupon") @RequestBody Coupon coupon);
}
