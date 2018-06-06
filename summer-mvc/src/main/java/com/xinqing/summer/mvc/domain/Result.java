package com.xinqing.summer.mvc.domain;

import com.xinqing.summer.mvc.http.RequestContext;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.Serializable;

/**
 * response result
 *
 * 设计参考spring mvc的返回格式
 *
 * Created by xuan on 2018/4/28
 */
public class Result<T> implements Serializable {

    /**
     * 可自定义status(可不同于http status)，与前端约定好即可
     *
     * 例如 -1为失败，0为成功，1为xxx不合法，2为xxx参数不正确等
     */
    private int status;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 具体的数据
     */
    private T data;

    /**
     * 当前请求路径path
     */
    private String path;

    /**
     * 时间撮
     */
    private long timestamp;

    private Result(int status, String message, T data, String path) {
        this(status, message, data, path, System.currentTimeMillis());
    }

    private Result(int status, String message, T data, String path, long timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.path = path;
        this.timestamp = timestamp;
    }

    /**
     * 根据data生成Result，其中status默认200，message为null
     *
     * @param data data
     * @param <T> T
     * @return Result<T>
     */
    public static <T> Result<T> of(T data) {
        return of(null, data);
    }

    /**
     * 根据message和data生成Result，其中status默认200
     *
     * @param message message
     * @param data data
     * @param <T> T
     * @return Result<T>
     */
    public static <T> Result<T> of(String message, T data) {
        return of(HttpResponseStatus.OK.code(), message, data);
    }

    /**
     * 根据status、message以及data生成Result
     *
     * @param status status
     * @param message message
     * @param data data
     * @param <T> T
     * @return Result<T>
     */
    public static <T> Result<T> of(int status, String message, T data) {
        String path = RequestContext.current().path();
        return new Result<>(status, message, data, path);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
