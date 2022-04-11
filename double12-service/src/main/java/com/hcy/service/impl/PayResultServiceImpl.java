package com.hcy.service.impl;

import com.hcy.entity.OrderState;
import com.hcy.service.MqService;
import com.hcy.service.PayResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 18:49
 */
@Service
public class PayResultServiceImpl implements PayResultService {
    @Autowired
    private MqService mqService;
    @Override
    public void updateOrderState(String orderId, Boolean isPresale) {
        if (isPresale){
            // 如果是预售商品，则是只支付了定金，状态改为 3
            mqService.updateOrderState(orderId, OrderState.PAID_ONLY_PART);
        }else{
            // 如果不是预售商品，说明是 一次性全额支付 类型的商品，状态改为 1
            mqService.updateOrderState(orderId,OrderState.HAVE_PAID_ALL);
        }
    }

    @Override
    public void rollBackStock(Integer skuId, Integer num) {
        mqService.rollBackStock(skuId, num);
    }
}
