package com.xinqing.summer.mvc.route;

import com.xinqing.summer.mvc.http.handler.Handler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Set;

/**
 * Route
 *
 * Created by xuan on 2018/4/25
 */
public class Route {

    /**
     * 请求路径
     */
    private String path;

    /**
     * http method，例如GET、POST等
     */
    private Set<HttpMethod> methods;

    /**
     * http handler
     */
    private Handler handler;

    public Route(String path, Set<HttpMethod> methods, Handler handler) {
        this.path = path;
        this.methods = methods;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<HttpMethod> getMethods() {
        return methods;
    }

    public void setMethods(Set<HttpMethod> methods) {
        this.methods = methods;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
