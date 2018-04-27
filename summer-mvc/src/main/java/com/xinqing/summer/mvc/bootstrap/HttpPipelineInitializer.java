package com.xinqing.summer.mvc.bootstrap;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Http Pipeline
 *
 * Created by xuan on 2018/4/16
 */
@ChannelHandler.Sharable
public class HttpPipelineInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_SIZE = 65536;

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        // combination of HttpRequestDecoder and HttpResponseEncoder
        pipeline.addLast(new HttpServerCodec());
        // aggregates an HttpMessage and its following HttpContent into a single FullHttpRequest
        pipeline.addLast(new HttpObjectAggregator(MAX_SIZE));
        // HTTP compression
        pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpServerHandler());
    }
}
