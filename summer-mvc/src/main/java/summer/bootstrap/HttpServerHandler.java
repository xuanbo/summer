package summer.bootstrap;

import summer.http.HttpExecution;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Http Server Handler
 *
 * Created by xuan on 2018/4/16
 */
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerHandler.class);

    private final ThreadPoolExecutor threadPoolExecutor;
    private final HttpExecution execution;

    public HttpServerHandler(HttpExecution execution, ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.execution = execution;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 交给业务线程执行，io操作将会切换回netty
        threadPoolExecutor.execute(() -> execution.execute(ctx, request));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.warn("exceptionCaught", cause);
        ctx.close();
    }
}
