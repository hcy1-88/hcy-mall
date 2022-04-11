package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/9 19:04
 */
@TableName("hcy_preview_goods_img")
@Data
public class PreviewGoodsImg {
    @TableField(value = "id")
    private String id;
    @TableField(value = "goods_id")
    private String goodsId;
    @TableField(value = "img_url")
    private String imgUrl;
}
