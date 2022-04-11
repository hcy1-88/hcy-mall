package com.hcy.controller;

import com.hcy.pojo.R;
import com.hcy.service.PayResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 18:11
 */
@RestController
@RequestMapping("/payResult")
public class PayResultController {
    @Autowired
    private PayResultService payResultService;

    @RequestMapping("/paySuccess")
    public R paySuccess(@RequestParam("orderId") String orderId,@RequestParam("isPresale") Boolean isPresale) {
        payResultService.updateOrderState(orderId, isPresale);
        return new R(1001,true,new HashMap<>());
    }

    @RequestMapping("/giveUpPaying")
    public R giveUpPaying(@RequestParam("skuId") Integer skuId,@RequestParam("num") Integer num) {
        //回滚库存
        payResultService.rollBackStock(skuId, num);
        return new R(1001,true,new HashMap<>());
    }
}
