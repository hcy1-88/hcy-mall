package com.hcy.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/2 23:50
 */
public class ExceptionUtil {
    public static String exceptionToString(Throwable t){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        t.printStackTrace(pout);
        return new String(out.toByteArray());
    }
}
