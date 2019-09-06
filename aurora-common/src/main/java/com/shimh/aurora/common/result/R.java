package com.shimh.aurora.common.result;

import com.shimh.aurora.common.constant.ResultCode;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
@Data
@Builder
public class R {
    private String msg;

    private Integer code;

    private Object data;

    public R() {
    }

    public R(String msg, int code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public R(ResultCode resultCode, Object data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public static R success() {
        return new R(ResultCode.SUCCESS, null);
    }
    public static R success(Object data) {
        return success(ResultCode.SUCCESS, data);
    }

    public static R success(ResultCode resultCode, Object data) {
        return new R(resultCode, data);
    }

    public static R error() {
        return error(ResultCode.ERROR, null);
    }

    public static R error(Object data) {
        return error(ResultCode.ERROR, data);
    }

    public static R error(ResultCode resultCode, Object data) {
        return new R(resultCode, data);
    }


    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }

    public R m () {
        this.data = new HashMap<String, Object>();
        return this;
    }

    public R put (String key, Object value) {
        if (this.data == null) {
            throw new NullPointerException("call method m first");
        }
        Map<String, Object> data = (Map<String, Object>) this.data;
        data.put(key, value);
        return this;
    }

    public static void main(String[] args) {
        System.out.println(R.success());
        System.out.println(R.success().m().put("k1", "v1").put("k2", "v2"));
        System.out.println(R.success("ddd"));
        System.out.println(new R());
    }

}
