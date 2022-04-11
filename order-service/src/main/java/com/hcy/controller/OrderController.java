package com.hcy.controller;


import com.hcy.dto.OrderForUser;
import com.hcy.pojo.R;
import com.hcy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/getOrderForUserByUid")
    public List<OrderForUser> getOrderForUserByUid(String uid) {
        return orderService.findOrderForUserByUid(uid);
    }

    @RequestMapping("/payRemain")
    public String payRemain(@RequestParam("orderId") String orderId,@RequestParam("factPay")String factPay, @RequestParam("state")Integer state) {
        orderService.payTailMoney(orderId,factPay,state);
        return "ok";
    }
}

