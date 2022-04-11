package com.hcy.dto;

import com.hcy.entity.Coupon;
import com.hcy.entity.Discount;
import com.hcy.entity.FullReduce;
import com.hcy.entity.Presale;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/9 19:32
 */
@Data
@ToString
public class GoodsForDetail implements Serializable {
    private Integer skuId;
    private String sellerId;
    private String goodsId;
    private String goodsName;
    private String sku;
    private String originalPrice;
    private String nowPrice;
    private int stock;
    private String promotionWay;
    private List<String> previewImgs;
    private List<String> detailImgs;
    private Presale presale;
    private Coupon coupons;
    private List<FullReduce> fullReduces;
    private List<Discount> discounts;
}
