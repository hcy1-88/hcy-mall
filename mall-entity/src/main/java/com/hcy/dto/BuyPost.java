package com.hcy.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/22 22:44
 */
@Data
public class BuyPost implements Serializable {
    private String orderId;
    private Integer skuId;
    private Boolean isPresale;
    private Integer num;
    private BigDecimal factMoney;
    private Integer addressId;
    private String buyerId;
    private String sellerId;
    private String payWay;
}
