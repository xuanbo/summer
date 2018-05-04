package summer.http;

import summer.http.handler.Before;
import summer.http.handler.Handler;
import summer.route.Route;
import summer.route.Router;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * http执行
 *
 * Created by xuan on 2018/4/17
 */
public class HttpExecution {

    private static final Logger LOG = LoggerFactory.getLogger(HttpExecution.class);

    private final Router router;

    public HttpExecution(Router router) {
        this.router = router;
    }

    public void execute(ChannelHandlerContext ctx, FullHttpRequest req) {
        Request request = new DefaultRequest(req);
        Response response = new DefaultResponse(ctx, HttpUtil.isKeepAlive(req));
        // 初始化请求上下文
        RequestContext.setup(request);
        try {
            // request execute
            doExecute(request, response);
        } catch (Exception e) {
            LOG.warn("request handle error", e);
            // failure handler
            router.failureHandler().handle(request, response, e);
        }
        // 释放请求上下文
        RequestContext.cleanup();
    }

    private void doExecute(Request request, Response response) {
        String path = request.path();

        // 前置拦截
        List<Before> befores = router.lookup(path);
        for (Before before : befores) {
            if (!before.doBefore(request, response)) {
                return;
            }
        }

        // 处理路由
        Handler handler;
        // 寻找到匹配的handler
        Route route = router.lookup(request.method(), path);
        if (route == null) {
            // not found
            handler = router.notFound();
        } else {
            handler = route.getHandler();
        }
        // do handle
        handler.handle(request, response);
    }

}
