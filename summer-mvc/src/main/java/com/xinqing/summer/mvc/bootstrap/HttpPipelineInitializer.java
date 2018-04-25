package com.xinqing.summer.mvc.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Http Pipeline
 *
 * Created by xuan on 2018/4/16
 */
@ChannelHandler.Sharable
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private static final int MAX_SIZE = 512 * 1024;

    @Override
    protected void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        // combination of HttpRequestDecoder and HttpResponseEncoder
        pipeline.addLast(new HttpServerCodec());
        // aggregates an HttpMessage and its following HttpContent into a single FullHttpRequest
        pipeline.addLast(new HttpObjectAggregator(MAX_SIZE));
        pipeline.addLast(new HttpServerHandler());
    }
}
