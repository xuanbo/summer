package com.xinqing.summer.mvc.http.handler;

import com.xinqing.summer.mvc.domain.Error;
import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * not found
 *
 * Created by xuan on 2018/4/25
 */
public class NotFoundHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {
        response.status(HttpResponseStatus.NOT_FOUND);
        Error error = resolveNotFoundError(request, response);
        response.json(error);
    }

    private Error resolveNotFoundError(Request request, Response response) {
        int status = response.status().code();
        String path = request.path();
        String message = HttpResponseStatus.NOT_FOUND.reasonPhrase();
        return new Error(status, message, null, path);
    }

}
