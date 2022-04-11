package com.hcy.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hcy
 * @since 2022-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hcy_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("sku_id")
    private Integer skuId;

    /**
     * 购买数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 总价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 交易编号，支付后回填
     */
    @TableField("trade_no")
    private String tradeNo;

    /**
     * 用户id
     */
    @TableField("buyer_id")
    private String buyerId;

    /**
     * 0表示未支付，1表示已经支付，2表示支付失败; 3表示未完全支付，只支付了一部分
     */
    @TableField("state")
    private Integer state;
    @TableField("address_id")
    private Integer addressId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField("seller_id")
    private String sellerId;

    /**
     * 支付方式
     */
    @TableField("pay_way")
    private String payWay;

    /**
     * 物流id，用于获取运单号、物流方式、货物的运程信息
     */
    @TableField("express_id")
    private String expressId;


}
