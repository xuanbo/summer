package com.xinqing.summer.mvc.example.controller;

import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;

/**
 * Created by xuan on 2018/4/17
 */
public class ExampleController {

    public void text(Request request, Response response) {
        response.text("Hello World");
    }

    public void json(Request request, Response response) {
        response.json("Hello World");
    }

}
