package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.HttpMethod;

/**
 * http request
 *
 * Created by xuan on 2018/4/17
 */
public interface Request {

    /**
     * http request uri
     *
     * @return uri
     */
    String uri();

    /**
     * http method
     *
     * @return HttpMethod
     */
    HttpMethod method();

}
