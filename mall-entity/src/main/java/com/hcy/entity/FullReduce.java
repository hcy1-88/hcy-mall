package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description：满减活动
 * Author: 黄成勇
 * Date:  2022/3/4 22:54
 */
@Data
@TableName("hcy_full_reduce")
public class FullReduce implements Serializable {
    @TableId("id")
    private String id;
    @TableField("goods_id")
    private String goodsId;
    @TableField("full")
    private BigDecimal full;  // 满多少元
    @TableField("reduce")
    private BigDecimal reduce;  // 减多少元
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
