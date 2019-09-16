package com.shimh.aurora.common.constant;

/**
 * @author: shimh
 * @create: 2019年08月
 **/
public enum  ResultCode {

    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    REDIRECT(3, "重定向"),

    USER_NOT_EXIST(2000, "用户不存在"),
    USER_LOGIN_ERROR(2001, "用户名或密码错误");



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
