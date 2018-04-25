package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

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

}
