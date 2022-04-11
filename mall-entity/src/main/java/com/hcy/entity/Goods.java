package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
@TableName("hcy_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;
    @TableField(value = "goods_name")
    private String goodsName;
    @TableField(value = "original_price")
    private BigDecimal originalPrice;
    @TableField(value = "now_price")
    private BigDecimal nowPrice;
    @TableField(value = "brand")
    private String brand;
    @TableField(value = "img_url")
    private String imgUrl;

}
