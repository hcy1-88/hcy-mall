package com.hcy.dto;

import lombok.Data;

/**
 * Description：这是返回给前端的数据
 * Author: hcy
 * Date:  2021/10/27 1:39
 */
@Data
public class GoodsForAdmin {
    private String goodsName;
    private SkuForAdmin sku;
    private String imgUrl;

    @Override
    public String toString() {
        return "GoodsForAdmin{" +
                "goodsName='" + goodsName + '\'' +
                ", sku=" + sku +
                '}';
    }
}
