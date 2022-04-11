package com.hcy.service.impl;

import com.hcy.mapper.StockMapper;
import com.hcy.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 15:06
 */
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;
    @Override
    public int reduceStock(Integer skuId,Integer num) {
        return stockMapper.reduceStockBySkuId(skuId,num);
    }

    @Override
    public int rollbackStock(Integer skuId, Integer num) {
        return stockMapper.addStockBySkuId(skuId, num);
    }
}
