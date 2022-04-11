package com.hcy.pojo;

import java.util.Map;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/26 22:51
 */
public class R {
    private Integer code;
    private boolean success;
    private Map<String,Object> res;

    public R() {
    }

    public R(Integer code, boolean success, Map<String, Object> res) {
        this.code = code;
        this.success = success;
        this.res = res;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getRes() {
        return res;
    }

    public void setRes(Map<String, Object> res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", success=" + success +
                ", res=" + res +
                '}';
    }
}
