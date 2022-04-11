package com.hcy.controller;

import com.hcy.dto.BuyPost;
import com.hcy.dto.GoodsForConfirmOrder;
import com.hcy.entity.PromotionGoods;
import com.hcy.entity.Sku;
import com.hcy.feign.GoodsFeign;
import com.hcy.feign.PromotionGoodsClient;
import com.hcy.pojo.R;
import com.hcy.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 11:05
 */
@RestController
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;


    @RequestMapping("/confirmBuy")
    public R confirmBuy(@RequestBody BuyPost buyPost) {
        System.out.println(buyPost);
        return promotionService.buy(buyPost);
    }

    @RequestMapping("/setStock2Redis")
    public String setStock2Redis(Integer skuId){
        return promotionService.setStock(skuId);
    }

    @RequestMapping("/findSkuForConfirmOrder")
    public GoodsForConfirmOrder findSkuForConfirmOrder(Integer skuId) {
        return promotionService.findSkuOrderConfirm(skuId);
    }

    @RequestMapping("/grabCoupon")
    public R grabCoupon(@RequestParam("userId") String userId, @RequestParam("goodsId") String goodsId) {
        return promotionService.reduceCoupon(userId,goodsId);
    }

    @PostConstruct
    public void initToRedis(){
        promotionService.setStockForAllHotGoods();
        promotionService.setCouponToRedis();
    }
}
