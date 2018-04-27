package com.xinqing.summer.mvc.http;

import com.xinqing.summer.mvc.json.JsonFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.stream.ChunkedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * default http response
 *
 * Created by xuan on 2018/4/17
 */
public class DefaultResponse implements Response {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResponse.class);

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
    public void status(HttpResponseStatus status) {
        raw.setStatus(status);
    }

    @Override
    public HttpResponseStatus status() {
        return raw.status();
    }

    @Override
    public void write(byte[] bytes) {
        raw.content().writeBytes(bytes);
        // 设置content-length
        headers().set(HttpHeaderNames.CONTENT_LENGTH, raw.content().readableBytes());
        // 发送response
        end();
    }

    @Override
    public void text(String text) {
        headers().set(HttpHeaderNames.CONTENT_TYPE, ContentType.TEXT_PLAIN_UTF8.value());
        write(text.getBytes());
    }

    @Override
    public void json(String json) {
        headers().set(HttpHeaderNames.CONTENT_TYPE, ContentType.APPLICATION_JSON_UTF8.value());
        write(json.getBytes());
    }

    @Override
    public void json(Object obj) {
        headers().set(HttpHeaderNames.CONTENT_TYPE, ContentType.APPLICATION_JSON_UTF8.value());
        write(JsonFactory.get().toJson(obj).getBytes());
    }

    @Override
    public void sendFile(File file) throws IOException {
        sendFile(null, file);
    }

    @Override
    public void sendFile(HttpHeaders headers, File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");

        long fileLength = raf.length();

        // if you write a FullHttp* message it contains the whole body of the message
        HttpResponse response;
        if (headers == null) {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        } else {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, headers);
        }

        // content-length
        HttpUtil.setContentLength(response, fileLength);
        // content-type
        setContentType(response, file);
        // keep alive
        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        // Write the initial line and the header.
        ctx.write(response);

        // Write the content.
        // HttpChunkedInput will write the end marker (LastHttpContent) for us.
        ChannelFuture future = ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf)), ctx.newProgressivePromise());

        future.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationComplete(ChannelProgressiveFuture future) {
                LOG.debug("Transfer complete");
            }

            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                // total unknown
                if (total < 0) {
                    LOG.error("Transfer progress: {}, total: {}", progress, total);
                } else {
                    LOG.trace("Transfer progress: {} / {}", progress, total);
                }
            }
        });

        // Decide whether to close the connection or not.
        if (!keepAlive) {
            // Close the connection when the whole content is written out.
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void redirect(String target) {
        // 302
        status(HttpResponseStatus.FOUND);
        // Location
        headers().set(HttpHeaderNames.LOCATION, target);
        // send response
        end();
    }

    private void end() {
        if (keepAlive) {
            headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(raw);
        } else {
            ctx.writeAndFlush(raw).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void setContentType(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
    }

}
