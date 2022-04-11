package com.hcy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/28 10:50
 */
@Data
public class OrderForUser {
    public static int NO_PAID_NO_DELIVER = 0;  // 未支付，未发货
    public static int PAID_NO_DELIVER = 1;  // 已支付，未发货
    public static int PAID_DELIVERED_NO_RECEIVE = 2; // 已支付、已发货、未收货
    public static int PAID_DELIVERED_RECEIVED = 3;  // 已支付、已发货、已经收货
    public static int ONLY_PAID_PART = 4;  // 只支付了一部分，还有另外部分没付钱

    private String imgUrl;
    private String orderId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private String goodsName;
    private String goodsId;
    private String sku;
    private String unitPrice;  // 单价
    private int num;
    private String factMoney;  // 实付款
    private String remainToPay;  // 尚未支付的尾款
    private int tradeState;  // 交易状态，上面4种
    private boolean isPresale;  // 是不是预售商品
    private String expressId;  // 物流ID
    private String payWay;
}
