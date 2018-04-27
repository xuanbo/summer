package com.xinqing.summer.mvc;

import com.xinqing.summer.mvc.bootstrap.HttpPipelineInitializer;
import com.xinqing.summer.mvc.bootstrap.HttpServer;
import com.xinqing.summer.mvc.bootstrap.HttpServerHandler;
import com.xinqing.summer.mvc.http.HttpExecution;
import com.xinqing.summer.mvc.http.handler.Before;
import com.xinqing.summer.mvc.http.handler.Handler;
import com.xinqing.summer.mvc.route.Router;
import com.xinqing.summer.mvc.route.RouterImpl;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * bootstrap
 *
 * Created by xuan on 2018/4/16
 */
public class Summer {

    private static final Logger LOG = LoggerFactory.getLogger(Summer.class);

    private final Router router = new RouterImpl();
    private int port = 9000;

    public Router router() {
        return router;
    }

    public Summer before(String ant, Before before) {
        router.before(ant, before);
        return this;
    }

    public Summer get(String path, Handler handler) {
        router.get(path, handler);
        return this;
    }

    public Summer post(String path, Handler handler) {
        router.post(path, handler);
        return this;
    }

    public Summer put(String path, Handler handler) {
        router.put(path, handler);
        return this;
    }

    public Summer delete(String path, Handler handler) {
        router.delete(path, handler);
        return this;
    }

    public Summer route(String path, Set<HttpMethod> methods, Handler handler) {
        router.route(path, methods, handler);
        return this;
    }

    public Summer listen(int port) {
        this.port = port;
        return this;
    }

    public void serve() {
        try {
            // 启动http server
            server().listenAndServe(port);
        } catch (Exception e) {
            LOG.error("listenAndServe error.", e);
        }
    }

    private HttpServer server() {
        HttpExecution execution = new HttpExecution(router);
        HttpServerHandler httpServerHandler = new HttpServerHandler(execution);
        HttpPipelineInitializer httpPipelineInitializer = new HttpPipelineInitializer(httpServerHandler);
        return new HttpServer(httpPipelineInitializer);
    }

}
