package com.hcy;

import com.hcy.dto.GoodsForDetail;
import com.hcy.dto.GoodsForSeller;
import com.hcy.entity.Goods;
import com.hcy.entity.Sku;
import com.hcy.service.GoodsService;
import com.hcy.service.SkuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/11/30 15:52
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class MyTest {
    @Autowired
    private SkuService skuService;
    @Autowired
    private GoodsService goodsService;
    @Test
    public void test(){
        Sku sku = new Sku();
        Goods goods = new Goods();
        goods.setGoodsName("小米手机");
        goods.setBrand("小米");
        goods.setNowPrice(new BigDecimal("12.60"));
        goods.setOriginalPrice(new BigDecimal("13.88"));
        sku.setStatus(0);
        sku.setStock(50);
        sku.setSellerId("1");
        sku.setSku("{\"颜色\":\"红色\"}");
        sku.setGoods(goods);
        String s = skuService.insertSku(sku);
        System.out.println(s);
    }

    @Test
    public void test02(){
        List<GoodsForSeller> goodsSkuBySellerId = skuService.getGoodsSkuBySellerId("1", 1, 2);
        System.out.println(goodsSkuBySellerId.size());
        goodsSkuBySellerId.forEach(System.out::println);
    }

    @Test
    public void testDetail(){
        List<GoodsForDetail> detailByGoodsId = goodsService.findDetailByGoodsId("1465865873104441344");
        detailByGoodsId.forEach(System.out::println);
    }

    @Test
    public void testStock(){
        Integer stockBySkuId = skuService.findStockBySkuId(12);
        System.out.println(stockBySkuId);
    }

}
