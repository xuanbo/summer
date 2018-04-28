package com.xinqing.summer.mvc.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.multipart.FileUpload;

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
     * 根据name获取path上的变量
     * 例如/user/:id，当请求为/user/1时，则path(id)为1
     *
     * @param name the name of the pattern
     * @return value，不存在为null
     */
    String path(String name);

    /**
     * 根据name获取path上的int型变量
     * 例如/user/:id，当请求为/user/1时，则path(id)为1
     *
     * @param name the name of the pattern
     * @return Integer value of the pattern
     */
    Integer pathInt(String name);

    /**
     * 根据name获取path上的long型变量
     * 例如/user/:id，当请求为/user/1时，则path(id)为1
     *
     * @param name the name of the pattern
     * @return Long value of the pattern
     */
    Long pathLong(String name);

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
     * @see Request#paramValues(java.lang.String)
     *
     * @param name the name of the parameter
     * @return single value of the parameter
     */
    String param(String name);

    /**
     * 获取queryString上的参数值
     * 例如：/a/b?c=1&d=2&c=3；则d=2，c=1
     * 没有对应的参数时，会返回null
     *
     * @see Request#param(java.lang.String)
     *
     * @param name the name of the parameter
     * @return Integer value of the parameter
     */
    Integer paramInt(String name);

    /**
     * 获取queryString上的参数值
     * 例如：/a/b?c=1&d=2&c=3；则d=2，c=1
     * 没有对应的参数时，会返回null
     *
     * @see Request#param(java.lang.String)
     *
     * @param name the name of the parameter
     * @return Long value of the parameter
     */
    Long paramLong(String name);

    /**
     * 获取queryString上的参数值
     * 例如：/a/b?c=1&d=2&c=3；则d=2，c=1
     * 没有对应的参数时，会返回null
     *
     * @see Request#param(java.lang.String)
     *
     * @param name the name of the parameter
     * @return Boolean value of the parameter
     */
    Boolean paramBool(String name);

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

    /**
     * 获取http request body内容
     *
     * @return ByteBuf
     */
    ByteBuf content();

    /**
     * 获取http body中的内容，会覆盖相同的name，不支持application/json
     *
     * @see Request#json(java.lang.Class)
     *
     * @return Map<String, String>
     */
    Map<String, String> body();

    /**
     * 当请求头为application/json时，可以获取将body转为需要对象
     *
     * @see Request#body()
     *
     * @param requireClazz 将body转为需要对象
     * @param <T> T
     * @return T
     */
    <T> T json(Class<T> requireClazz);

    /**
     * 文件上传，会顺便提取http body中的内容，即初始化body()方法
     *
     * @return Map<String, List<FileUpload>>
     */
    Map<String, List<FileUpload>> files();

    /**
     * 文件上传
     *
     * @param name the name of the parameter
     * @return List<FileUpload>
     */
    List<FileUpload> file(String name);

}
