package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/4 23:01
 */
@Data
@TableName("hcy_discount")
public class Discount {
    @TableId("id")
    private String id;
    @TableField("num")
    private Integer num;  // 买多少件打折
    @TableField("discount")
    private Float discount;  // 折扣
    @TableField("goods_id")
    private String goodsId;
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    @TableField("expire_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date expireTime;
    @TableField("enable")
    private Boolean enable;
}
