package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/18 18:33
 */
@TableName("hcy_address")
@Data
public class Address {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private String userId;
    @TableField("region")
    private String region;
    @TableField("street")
    private String street;
    @TableField("tag")
    private String tag;
}
