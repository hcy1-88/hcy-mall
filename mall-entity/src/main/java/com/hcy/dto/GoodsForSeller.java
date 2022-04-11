package com.hcy.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Description：返回给前端的数据
 * Author: 黄成勇
 * Date:  2021/11/30 15:15
 */
@Data
@ToString
public class GoodsForSeller {
    private String goodsId;
    private String goodsName;
    private String originalPrice;
    private String nowPrice;
    private String brand;
    private String imgUrl;
    private SkuForSeller skuForSeller;
}
