package com.hcy.service;

import org.springframework.stereotype.Service;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 15:03
 */
public interface StockService {
    int reduceStock(Integer skuId,Integer num);
    int rollbackStock(Integer skuId,Integer num);
}
