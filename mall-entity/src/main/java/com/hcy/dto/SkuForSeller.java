package com.hcy.dto;

import lombok.Data;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/11/30 15:13
 */
@Data
public class SkuForSeller {
    private Integer skuId;
    private String sku;
    private int stock;
    private int status;
}
