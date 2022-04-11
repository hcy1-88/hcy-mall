package com.hcy.util;

import com.hcy.dto.BuyPost;
import com.hcy.entity.Order;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 15:40
 */
public class OrderUtil {
    public static Order buyPostToOrder(BuyPost buyPost){
        Order order= new Order();
        order.setId(buyPost.getOrderId());
        order.setSkuId(buyPost.getSkuId());
        order.setNum(buyPost.getNum());
        order.setPrice(buyPost.getFactMoney());
        order.setAddressId(buyPost.getAddressId());
        order.setBuyerId(buyPost.getBuyerId());
        order.setSellerId(buyPost.getSellerId());
        order.setPayWay(buyPost.getPayWay());
        return order;
    }
}
