package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/4 17:01
 */
@Data
@TableName("hcy_promotion_goods")
public class PromotionGoods {
    @TableId("id")
    private String id;
    @TableField("goods_id")
    private String goodsId;
}
