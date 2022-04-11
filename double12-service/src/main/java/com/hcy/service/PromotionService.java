package com.hcy.service;

import com.hcy.dto.BuyPost;
import com.hcy.dto.GoodsForConfirmOrder;
import com.hcy.pojo.R;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 11:05
 */
public interface PromotionService {
    R buy(BuyPost buyPost);
    String setStock(Integer skuId);
    GoodsForConfirmOrder findSkuOrderConfirm(Integer skuId);
    void setStockForAllHotGoods();
    void setCouponToRedis();
    R reduceCoupon(String userId,String goodsId);
}
