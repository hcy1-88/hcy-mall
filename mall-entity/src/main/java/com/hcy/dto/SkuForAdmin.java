package com.hcy.dto;

import lombok.Data;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/27 1:40
 */
@Data
public class SkuForAdmin {
    private Integer skuId;
    private int stock;
    private String sku;
    private String sellerName;
    private String sellerPhone;
    private int status;

    @Override
    public String toString() {
        return "SkuForAdmin{" +
                "skuId=" + skuId +
                ", stock=" + stock +
                ", sku='" + sku + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerPhone='" + sellerPhone + '\'' +
                ", status=" + status +
                '}';
    }
}
