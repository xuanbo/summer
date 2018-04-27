package com.xinqing.summer.mvc.http.handler;

import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;

/**
 * 前置拦截
 *
 * Created by xuan on 2018/4/27
 */
public interface Before {

    /**
     * 过滤请求，返回true则会放行，false则停止
     *
     * @param request Request
     * @param response Response
     * @return true则会放行，false则停止
     */
    boolean doBefore(Request request, Response response);

}
