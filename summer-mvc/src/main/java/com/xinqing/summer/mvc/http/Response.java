package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

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
     * 获取请求头
     *
     * @param header header key
     * @return header value
     */
    String header(String header);

    /**
     * 设置请求头
     *
     * @param header header key
     * @param value header value
     */
    void header(String header, Object value);

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
     * 重定向
     *
     * @param target 重定向路径
     */
    void redirect(String target);

}
