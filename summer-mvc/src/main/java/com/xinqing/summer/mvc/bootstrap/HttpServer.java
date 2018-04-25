package com.xinqing.summer.mvc.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http Server
 *
 * Created by xuan on 2018/4/16
 */
public class HttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    private volatile boolean running;

    private HttpPipelineInitializer initializer = new HttpPipelineInitializer();

    public HttpServer() {
    }

    public void listenAndServe(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer);

            Channel ch = bootstrap.bind(port).sync().channel();

            running = true;
            LOG.info("listen port: {}", port);

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setInitializer(HttpPipelineInitializer initializer) {
        this.initializer = initializer;
    }
}
