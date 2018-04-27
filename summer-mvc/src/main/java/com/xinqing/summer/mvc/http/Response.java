package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.File;
import java.io.IOException;

/**
 * http response
 *
 * Created by xuan on 2018/4/17
 */
public interface Response {

    /**
     * 获取netty封装的FullHttpResponse
     *
     * @return FullHttpResponse
     */
    FullHttpResponse raw();

    /**
     * 请求头
     *
     * @return HttpHeaders
     */
    HttpHeaders headers();

    /**
     * 设置http response status
     *
     * @param status HttpResponseStatus
     */
    void status(HttpResponseStatus status);

    /**
     * 获取http response status
     *
     * @return HttpResponseStatus
     */
    HttpResponseStatus status();

    /**
     * 字节数组写入响应
     *
     * @param bytes
     */
    void write(byte[] bytes);

    /**
     * 发送文本
     *
     * @param text 文本
     */
    void text(String text);

    /**
     * 发送json
     *
     * @param json json字符串
     */
    void json(String json);

    /**
     * 发送json
     *
     * @param obj 对象
     */
    void json(Object obj);

    /**
     * 发送文件
     * @see Response#sendFile(io.netty.handler.codec.http.HttpHeaders, java.io.File)
     *
     * @param file 文件
     * @throws IOException IOException
     */
    void sendFile(File file) throws IOException;

    /**
     * 发送文件时指定http header
     *
     *
     * @param headers HttpHeaders
     * @param file 文件
     * @throws IOException IOException
     */
    void sendFile(HttpHeaders headers, File file) throws IOException;

    /**
     * 重定向
     *
     * @param target 重定向路径
     */
    void redirect(String target);

}
