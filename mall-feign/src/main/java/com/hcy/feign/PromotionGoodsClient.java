package com.hcy.feign;

import com.hcy.entity.PromotionGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/4 17:14
 */
@FeignClient(contextId = "promotionGoodsClient", name = "goods-service",path = "/promotionGoods")
public interface PromotionGoodsClient {
    @RequestMapping("/getAllById") List<PromotionGoods> getAllPromotionGoodsId();
}
