package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

import java.util.List;
import java.util.Map;

/**
 * http request
 *
 * Created by xuan on 2018/4/17
 */
public interface Request {

    /**
     * http request uri
     * /a/b?c=1
     *
     * @return uri
     */
    String uri();

    /**
     * 获取请求路径
     * /a/b
     *
     * @return path
     */
    String path();

    /**
     * 获取path上的变量，例如/user/:id，当请求为/user/1时，则paths为{id:1}
     *
     * @return Map<String, String>
     */
    Map<String, String> paths();

    /**
     * 设置path上的变量，例如/user/:id，当请求为/user/1时，框架自动设置paths为{id:1}
     *
     * @param paths Map<String, String>
     */
    void paths(Map<String, String> paths);

    /**
     * 获取请求路径参数
     * c=1
     *
     * @return queryString
     */
    String queryString();

    /**
     * 获取queryString参数
     *
     * @return Map<String, List<String>>
     */
    Map<String, List<String>> params();

    /**
     * 获取queryString上的参数值
     * 例如：/a/b?c=1&d=2&c=3；则d=2，c=1
     * 没有对应的参数时，会返回null
     *
     * @param name the name of the parameter
     * @return single value of the parameter
     */
    String param(String name);

    /**
     * 获取queryString上的参数值
     * 例如：/a/b?c=1&d=2&c=3；则c=[1, 3]
     * 没有对应的参数时，会返回空集合
     *
     * @param name the name of the parameter
     * @return all value of the parameter
     */
    List<String> paramValues(String name);

    /**
     * http method
     *
     * @return HttpMethod
     */
    HttpMethod method();

    /**
     * 获取请求头
     *
     * @return HttpHeaders
     */
    HttpHeaders headers();

    /**
     * 获取请求头
     *
     * @param header header key
     * @return header value
     */
    String header(String header);

}
