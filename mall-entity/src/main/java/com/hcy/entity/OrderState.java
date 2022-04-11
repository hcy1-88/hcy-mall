package com.hcy.entity;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/28 11:01
 */
public class OrderState {
    public static int NO_PAID = 0;  // 未支付
    public static int HAVE_PAID_ALL = 1; // 已经完全支付
    public static int FAILED_PAY = 2; //支付失败
    public static int PAID_ONLY_PART = 3; // 只支付了一部分
}
