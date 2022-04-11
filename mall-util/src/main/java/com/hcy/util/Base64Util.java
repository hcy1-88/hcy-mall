package com.hcy.util;

import java.util.Base64;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/11/29 15:18
 */
public class Base64Util {
    public static String decode(String encrypted){
        byte[] decode = Base64.getDecoder().decode(encrypted);   // 解密
        String raw = new String(decode);  // 解密后的信息
        return raw;
    }
}
