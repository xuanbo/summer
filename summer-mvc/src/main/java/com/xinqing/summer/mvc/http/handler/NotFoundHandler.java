package com.xinqing.summer.mvc.http.handler;

import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;

/**
 * not found
 *
 * Created by xuan on 2018/4/25
 */
public class NotFoundHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {
        response.text("404 Not Found!");
    }

}
