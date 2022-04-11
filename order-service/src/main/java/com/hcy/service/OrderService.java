package com.hcy.service;

import com.hcy.dto.BuyPost;
import com.hcy.dto.OrderForUser;
import com.hcy.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcy
 * @since 2022-02-10
 */
public interface OrderService extends IService<Order> {
    void placeOrder(BuyPost buyPost);
    void updateState(String orderId,Integer state);
    List<OrderForUser> findOrderForUserByUid(String uid);
    void payTailMoney(String orderId,String factMoney,Integer state);
}
