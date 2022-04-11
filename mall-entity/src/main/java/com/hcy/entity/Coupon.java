package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/4 22:32
 */
@Data
@TableName("hcy_coupon")
public class Coupon implements Serializable {
    @TableField("id")
    private String id;
    @TableField("money_size")
    private BigDecimal moneySize;
    @TableField("num")
    private Integer num;
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
