package com.shimh.aurora.common.exception;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
public class AuroraException extends RuntimeException{

    private int code;

    public AuroraException() {
        super();
    }
    public AuroraException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AuroraException(String message) {
        super(message);
    }

    public AuroraException(String message,  Throwable cause) {
        super(message, cause);
    }

}
