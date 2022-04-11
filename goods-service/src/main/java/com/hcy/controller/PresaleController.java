package com.hcy.controller;

import com.hcy.entity.Presale;
import com.hcy.service.PresaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/3 19:29
 */
@RestController
@RequestMapping("/presale")
public class PresaleController {
    @Autowired
    private PresaleService presaleService;
    @RequestMapping("/getPresaleByGoodsId")
    public Presale getPresaleByGoodsId(@RequestParam("goodsId") String goodsId) {
        return presaleService.findPresaleByGoodsId(goodsId);
    }
}
