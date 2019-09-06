package com.shimh.aurora.common.constant;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
public enum  ResultCode {

    SUCCESS(0, "成功"),
    ERROR(1, "失败");



    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }





}
