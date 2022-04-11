package com.hcy.controller;


import com.hcy.dto.GoodsForAdmin;
import com.hcy.dto.GoodsForConfirmOrder;
import com.hcy.dto.GoodsForSearch;
import com.hcy.dto.GoodsForSeller;
import com.hcy.entity.Goods;
import com.hcy.entity.Sku;
import com.hcy.pojo.R;
import com.hcy.service.GoodsService;
import com.hcy.service.SkuService;
import com.hcy.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;
    @RequestMapping("/findAllGoodsForAdmin")
    public List<GoodsForAdmin> findAllGoods(){
        return skuService.findAllSku();
    }

    @RequestMapping("/modifySkuStatus")
    public R modifySkuStatus(int skuId,int status){
        return skuService.modifySkuStatus(skuId,status);
    }

    @RequestMapping("/findGoodsSkuBySellerId")
    public R findGoodsSkuBySellerId(@RequestParam("sellerId") String sellerId,@RequestParam("currPage") Integer currPage,@RequestParam("pageSize") Integer pageSize) {
        List<GoodsForSeller> goodsForSeller = skuService.getGoodsSkuBySellerId(sellerId, currPage, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("goodsForSeller",goodsForSeller);
        return new R(1001,true,res);
    }

    @RequestMapping("/addSku")
    public String addSku(@RequestBody Sku sku) {
        skuService.insertSku(sku);
        return "ok";
    }

    @RequestMapping("/updateGoodsAndSku")
    public R updateGoodsAndSku(@RequestBody Sku sku) {
        System.out.println(sku.getGoods());
        return skuService.updateSkuAndGoods(sku);
    }

    @RequestMapping("/deleteSku")
    public R deleteSku(@RequestParam("skuId") Integer skuId) {
        return skuService.deleteSkuById(skuId);
    }

    @RequestMapping("/totalGoodsNumForSeller")
    public Integer totalGoodsNumForSeller(String sellerId) {
        return skuService.countGoodsForSellerId(sellerId);
    }

    @RequestMapping("/search")
    public List<GoodsForSearch> search(@RequestParam("kw") String kw,@RequestParam("currPage") Integer currPage,@RequestParam("pageSize") Integer pageSize) {
        return skuService.searchGoods(kw,currPage,pageSize);
    }

    @RequestMapping("/findSkuForConfirmOrder")
    public GoodsForConfirmOrder findSkuById(Integer skuId) {
        return skuService.findSkuOrderConfirm(skuId);
    }

    @RequestMapping("/getStockBySkuId")
    public Integer getStockBySkuId(Integer skuId) {
        return skuService.findStockBySkuId(skuId);
    }

    @RequestMapping("/getSkuByGoodsId")
    public List<Sku> getSkuByGoodsId(String goodsId) {
        return skuService.findSkuByGoodsId(goodsId);
    }
}

