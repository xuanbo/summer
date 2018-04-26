package com.xinqing.summer.mvc.exception;

/**
 * json解析异常
 *
 * Created by xuan on 2018/4/26
 */
public class JsonException extends RuntimeException {

    public JsonException() {
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }
}
