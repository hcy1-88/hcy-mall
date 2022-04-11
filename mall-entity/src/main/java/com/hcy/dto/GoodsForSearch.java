package com.hcy.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/1 16:02
 */
@Data
public class GoodsForSearch {
    private String goodsId;
    private String sellerId;
    private String sellerName;
    private String goodsName;
    private BigDecimal nowPrice;
    private String imgUrl;
    private String brand;
}
