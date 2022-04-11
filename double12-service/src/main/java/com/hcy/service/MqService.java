package com.hcy.service;

import com.hcy.dto.BuyPost;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/24 20:06
 */
public interface MqService {
    void sendToStock(Integer skuId,Integer num);
    void sendToOrder(BuyPost buyPost);
    void updateOrderState(String orderId,Integer state);
    void rollBackStock(Integer skuId,Integer num);
}
