package com.xinqing.summer.mvc.route;

import com.xinqing.summer.mvc.exception.RouteException;
import com.xinqing.summer.mvc.http.handler.Handler;
import com.xinqing.summer.mvc.http.handler.NotFoundHandler;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuan on 2018/4/25
 */
public class RouterImpl implements Router {

    private static final Logger LOG = LoggerFactory.getLogger(RouterImpl.class);

    private static final String PATH_START = "/";

    private final Map<String, Route> routes = new ConcurrentHashMap<>();

    private Handler notFoundHandler = new NotFoundHandler();

    @Override
    public void get(String path, Handler handler) {
        Set<HttpMethod> methods = new HashSet<>();
        methods.add(HttpMethod.GET);
        route(path, methods, handler);
    }

    @Override
    public void post(String path, Handler handler) {
        Set<HttpMethod> methods = new HashSet<>();
        methods.add(HttpMethod.POST);
        route(path, methods, handler);
    }

    @Override
    public void put(String path, Handler handler) {
        Set<HttpMethod> methods = new HashSet<>();
        methods.add(HttpMethod.PUT);
        route(path, methods, handler);
    }

    @Override
    public void delete(String path, Handler handler) {
        Set<HttpMethod> methods = new HashSet<>();
        methods.add(HttpMethod.DELETE);
        route(path, methods, handler);
    }

    @Override
    public void route(String path, Set<HttpMethod> methods, Handler handler) {
        checkPath(path);
        Route route = new Route(path, methods, handler);
        addRoute(routes, route);
    }

    @Override
    public void notFound(Handler notFoundHandler) {
        if (notFoundHandler == null) {
            throw new NullPointerException();
        }
        this.notFoundHandler = notFoundHandler;
    }

    @Override
    public Handler notFound() {
        return notFoundHandler;
    }

    @Override
    public Route lookup(HttpMethod method, String path) {
        Route route = routes.get(path);
        // 精确匹配
        if (route != null) {
            Set<HttpMethod> methods = route.getMethods();
            if (methods.contains(method)) {
                LOG.debug("request '{}' matched by exact path '{}'", path, path);
                return route;
            } else {
                // 请求方法不支持
                LOG.debug("request '{}' method unsupported", path);
                return null;
            }
        }
        // 模糊匹配
        LOG.debug("request '{}' no matches found", path);
        return null;
    }

    /**
     * 检查请求路径是否合法，必须以'/'开头
     *
     * @param path 请求路径
     */
    private void checkPath(String path) {
        if (!StringUtils.startsWith(path, PATH_START)) {
            throw new RouteException("path must start with: " + PATH_START);
        }
    }

    private void addRoute(Map<String, Route> routes, Route route) {
        String path = route.getPath();
        if (routes.containsKey(path)) {
            throw new RouteException("mapping '" + path + "' added already");
        }
        LOG.info("mapping '{}' for {} onto {}", path, route.getMethods(), route.getHandler());
        routes.put(path, route);
    }

}
