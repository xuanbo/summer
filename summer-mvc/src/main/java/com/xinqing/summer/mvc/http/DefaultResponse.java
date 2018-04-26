package com.xinqing.summer.mvc.http;

import com.xinqing.summer.mvc.json.JsonFactory;
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
    public void status(HttpResponseStatus status) {
        raw.setStatus(status);
    }

    @Override
    public void text(String text) {
        header(HttpHeaderNames.CONTENT_TYPE.toString(), ContentType.TEXT_PLAIN_UTF8.value());
        write(text);
    }

    @Override
    public void json(String json) {
        header(HttpHeaderNames.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON_UTF8.value());
        write(json);
    }

    @Override
    public void json(Object obj) {
        header(HttpHeaderNames.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON_UTF8.value());
        write(JsonFactory.get().toJson(obj));
    }

    @Override
    public void redirect(String target) {
        status(HttpResponseStatus.FOUND);
        header(HttpHeaderNames.LOCATION.toString(), target);
        end();
    }

    private void write(String data) {
        raw.content().writeBytes(data.getBytes());
        // http状态码
        status(HttpResponseStatus.OK);
        // 设置content-length
        header(HttpHeaderNames.CONTENT_LENGTH.toString(), raw.content().readableBytes());
        // 发送response
        end();
    }

    private void end() {
        if (keepAlive) {
            headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(raw);
        } else {
            ctx.write(raw).addListener(ChannelFutureListener.CLOSE);
        }
    }

}
