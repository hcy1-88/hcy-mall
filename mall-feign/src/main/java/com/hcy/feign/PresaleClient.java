package com.hcy.feign;

import com.hcy.entity.Presale;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/3 19:41
 */
@FeignClient(contextId = "presaleClient", name = "goods-service",path = "/presale")
public interface PresaleClient {
    @RequestMapping("/getPresaleByGoodsId") Presale getPresaleByGoodsId(@RequestParam("goodsId") String goodsId);
}
