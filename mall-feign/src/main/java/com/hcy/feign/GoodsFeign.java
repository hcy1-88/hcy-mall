package com.hcy.feign;

import com.hcy.dto.GoodsForAdmin;
import com.hcy.dto.GoodsForConfirmOrder;
import com.hcy.dto.GoodsForDetail;
import com.hcy.dto.GoodsForSeller;
import com.hcy.entity.Sku;
import com.hcy.pojo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/27 12:59
 */
@FeignClient(contextId = "goodsClient", name = "goods-service",path = "/sku")
public interface GoodsFeign {
    @RequestMapping("/findAllGoodsForAdmin") List<GoodsForAdmin> findAllGoods();
    @RequestMapping("/modifySkuStatus") R modifySkuStatus(@RequestParam("skuId") int skuId,@RequestParam("status") int status);
    @RequestMapping("/findGoodsSkuBySellerId") List<GoodsForSeller> findGoodsSkuBySellerId(
            @RequestParam("sellerId") String sellerId,
            @RequestParam("currPage") Integer currPage,
            @RequestParam("pageSize") Integer pageSize
    );
    @RequestMapping("/getStockBySkuId")
    Integer getStockBySkuId(@RequestParam("skuId") Integer skuId);

    @RequestMapping("/findSkuForConfirmOrder")
    GoodsForConfirmOrder findSkuForConfirmOrder(@RequestParam("skuId") Integer skuId);
    @RequestMapping("/getSkuByGoodsId") List<Sku> getSkuByGoodsId(@RequestParam("goodsId") String goodsId);
}
