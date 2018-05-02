package com.xinqing.summer.mvc.http.handler;

import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;

/**
 * 失败handler
 *
 * Created by xuan on 2018/4/26
 */
@FunctionalInterface
public interface FailureHandler {

    /**
     * 失败处理
     *
     * @param request Request
     * @param response Response
     * @param t Throwable
     */
    void handle(Request request, Response response, Throwable t);

}
