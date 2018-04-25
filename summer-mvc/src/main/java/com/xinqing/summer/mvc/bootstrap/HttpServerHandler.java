package com.xinqing.summer.mvc.bootstrap;

import com.xinqing.summer.mvc.http.HttpExecution;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http Server Handler
 *
 * Created by xuan on 2018/4/16
 */
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerHandler.class);

    private final HttpExecution execution = new HttpExecution();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        execution.execute(ctx, request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.warn("exceptionCaught", cause);
        ctx.close();
    }
}
