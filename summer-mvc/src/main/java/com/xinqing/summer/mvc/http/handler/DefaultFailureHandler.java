package com.xinqing.summer.mvc.http.handler;

import com.xinqing.summer.mvc.domain.Error;
import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * 默认的失败处理
 *
 * Created by xuan on 2018/4/26
 */
public class DefaultFailureHandler implements FailureHandler {

    @Override
    public void handle(Request request, Response response, Throwable t) {
        if (response.status() == null) {
            response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
        Error error = resolveError(request, response, t);
        response.json(error);
    }

    private Error resolveError(Request request, Response response, Throwable t) {
        int status = response.status().code();
        String path = request.path();
        String message = t.getMessage();
        String exception = t.getClass().getName();
        return new Error(status, message, exception, path);
    }

}
