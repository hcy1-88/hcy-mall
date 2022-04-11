package com.hcy.feign;

import com.hcy.dto.GoodsForDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 20:29
 */
@FeignClient(contextId = "skuClient", name = "goods-service",path = "/goods")
public interface SkuFeign {
    // 页面详情页的数据
    @RequestMapping("/getDetailByGoodsId")
    List<GoodsForDetail> getDetailByGoodsId(@RequestParam("goodsId") String goodsId);
}
