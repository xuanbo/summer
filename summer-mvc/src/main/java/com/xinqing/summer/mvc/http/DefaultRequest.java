package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

/**
 * default http request
 *
 * Created by xuan on 2018/4/17
 */
public class DefaultRequest implements Request {

    private final FullHttpRequest raw;

    public DefaultRequest(FullHttpRequest raw) {
        this.raw = raw;
    }

    @Override
    public String uri() {
        return raw.uri();
    }

    @Override
    public HttpMethod method() {
        return raw.method();
    }
}
