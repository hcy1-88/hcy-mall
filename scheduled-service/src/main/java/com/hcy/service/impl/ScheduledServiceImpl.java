package com.hcy.service.impl;

import com.hcy.config.RedisConfig;
import com.hcy.entity.Coupon;
import com.hcy.feign.CouponFeign;
import com.hcy.service.ScheduledService;
import com.hcy.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/9 17:50
 */
@Service
public class ScheduledServiceImpl implements ScheduledService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private CouponFeign couponFeign;

    /**
      * @Description : 向 redis 发放优惠券，定时：每天晚上 8：00
      * @Date 2022/3/9 18:07
     **/
    @Scheduled(cron = "0 0 20 * * ?")
    @Override
    public void issueCoupon() {
        List<Coupon> allCoupon = couponFeign.getAllCoupon();
        allCoupon.forEach(coupon -> {
            ValueOperations<String, Object> sop = redisTemplate.opsForValue();
            // 如果redis已经存在这张优惠券，则设置失败；如果不存在，优惠券才可以设置成功
            sop.setIfAbsent(RedisConfig.COUPON_IN_REDIS_PREFIX+coupon.getGoodsId(),coupon.getNum());
        });
    }

    /**
      * @Description : 每天晚上 23：30：00 回收过期的优惠券；优惠券过期，应变成不可用的状态
      * @Date 2022/3/9 18:10
     **/
    @Override
    public void recycleCoupons() {
        List<Coupon> allCoupon = couponFeign.getAllCoupon();
        long time = new Date().getTime();
        allCoupon.forEach(coupon -> {
            if (coupon.getExpireTime().getTime() <= time){
                // 过期了
                coupon.setEnable(false);
                couponFeign.updateCoupon(coupon);
                // 删除优惠券
                redisTemplate.delete(RedisConfig.COUPON_IN_REDIS_PREFIX+coupon.getGoodsId());
                // 删除拥有该优惠券的用户
                Set<String> keys = RedisUtil.findKeyByPattern("CouponBeGrabbed::userid_*_goodsId_" + coupon.getGoodsId(), redisTemplate);
                keys.forEach(s -> {
                    redisTemplate.delete(s);
                });
            }
        });
    }


}
