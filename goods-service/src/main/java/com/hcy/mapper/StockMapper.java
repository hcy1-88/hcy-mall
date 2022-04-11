package com.hcy.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 15:05
 */
public interface StockMapper {
    int reduceStockBySkuId(@Param("skuId") Integer skuId, @Param("num") Integer num);
    int addStockBySkuId(@Param("skuId") Integer skuId, @Param("num") Integer num);
}
