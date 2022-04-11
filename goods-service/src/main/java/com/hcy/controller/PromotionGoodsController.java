package com.hcy.controller;

import com.hcy.entity.PromotionGoods;
import com.hcy.service.PromotionGoodsService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/4 17:06
 */
@RestController
@RequestMapping("/promotionGoods")
public class PromotionGoodsController {
    @Autowired
    private PromotionGoodsService promotionGoodsService;
    @RequestMapping("/getAllById")
    public List<PromotionGoods> getAllPromotionGoodsId() {
        return promotionGoodsService.list();
    }
}
