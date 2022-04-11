package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hcy_sku")
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sku_id", type = IdType.AUTO)
    private Integer skuId;
    @TableField("goods_id")
    private String goodsId;
    @TableField("sku")
    private String sku;
    @TableField("stock")
    private Integer stock;
    /**
     * 0 表示未审核，1 表示审核通过了，2表示审核不通过
     */
    @TableField("status")
    private Integer status;
    @TableField("seller_id")
    private String sellerId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private Goods goods;
    @TableField(exist = false)
    private Seller seller;

}
