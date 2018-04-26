package com.xinqing.summer.mvc.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * default http response
 *
 * Created by xuan on 2018/4/17
 */
public class DefaultResponse implements Response {

    private final FullHttpResponse raw;
    private final ChannelHandlerContext ctx;
    private final boolean keepAlive;

    public DefaultResponse(ChannelHandlerContext ctx, boolean keepAlive) {
        this.raw = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        this.ctx = ctx;
        this.keepAlive = keepAlive;
    }

    @Override
    public FullHttpResponse raw() {
        return raw;
    }

    @Override
    public HttpHeaders headers() {
        return raw.headers();
    }

    @Override
    public String header(String header) {
        return headers().get(header);
    }

    @Override
    public void header(String header, Object value) {
        headers().set(header, value);
    }

    @Override
    public void text(String text) {
        HttpHeaders headers = headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
        write(text);
    }

    @Override
    public void json(String json) {
        HttpHeaders headers = headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        write(json);
    }

    private void write(String data) {
        raw.content().writeBytes(data.getBytes());
        // http状态码
        raw.setStatus(HttpResponseStatus.OK);
        HttpHeaders headers = headers();
        // 设置content-length
        headers.set(HttpHeaderNames.CONTENT_LENGTH, raw.content().readableBytes());
        if (keepAlive) {
            headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(raw);
        } else {
            ctx.write(raw).addListener(ChannelFutureListener.CLOSE);
        }
    }

}
