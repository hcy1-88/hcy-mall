package com.hcy.service;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 18:48
 */
public interface PayResultService {
    void updateOrderState(String orderId,Boolean isPresale);
    void rollBackStock(Integer skuId,Integer num);
}
