package com.hcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/4 22:41
 */
@Data
@TableName("hcy_user_coupon")
public class UserCoupon implements Serializable {
    @TableId("id")
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("coupon_id")
    private String couponId;
    @TableField("enable")
    private Boolean enable;
}
