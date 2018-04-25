package com.xinqing.summer.mvc.route;

import com.xinqing.summer.mvc.http.handler.Handler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Set;

/**
 * Created by xuan on 2018/4/25
 */
public interface Router {

    /**
     * 注册http get handler
     *
     * @param path 请求路径
     * @param handler http handler
     */
    void get(String path, Handler handler);

    /**
     * 注册http post handler
     *
     * @param path 请求路径
     * @param handler http handler
     */
    void post(String path, Handler handler);

    /**
     * 注册http put handler
     *
     * @param path 请求路径
     * @param handler http handler
     */
    void put(String path, Handler handler);

    /**
     * 注册http delete handler
     *
     * @param path 请求路径
     * @param handler http handler
     */
    void delete(String path, Handler handler);

    /**
     * 注册路由，使支持多个httpMethod{GET、POST等}
     *
     * @param path 请求路径
     * @param methods Set<HttpMethod>
     * @param handler http handler
     */
    void route(String path, Set<HttpMethod> methods, Handler handler);

    /**
     * 注册http request not found handler
     *
     * @param notFoundHandler http handler
     */
    void notFound(Handler notFoundHandler);

    /**
     * 获取http request not found handler
     *
     * @return http handler
     */
    Handler notFound();

    /**
     * 路由匹配
     *
     * @param method http method
     * @param path 请求路径
     * @return Route
     */
    Route lookup(HttpMethod method, String path);

}
