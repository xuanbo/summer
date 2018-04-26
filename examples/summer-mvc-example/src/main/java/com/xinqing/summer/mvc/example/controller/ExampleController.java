package com.xinqing.summer.mvc.example.controller;

import com.alibaba.fastjson.JSON;
import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 例子
 *
 * Created by xuan on 2018/4/17
 */
public class ExampleController {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleController.class);

    public void text(Request request, Response response) {
        // 获取uri，例如/a/b?c=1&d=2&c=3
        LOG.info("uri: {}", request.uri());
        // 获取path，例如/a/b
        LOG.info("path: {}", request.path());
        // 获取queryString，例如c=1&d=2&c=3
        LOG.info("queryString: {}", request.queryString());
        // 获取params，例如{c: [1, 3], d: [2]}
        LOG.info("params: {}", request.params());
        response.text("Hello World");
    }

    public void json(Request request, Response response) {
        response.json("{\"data\": \"Hello World\"}");
    }

    public void path(Request request, Response response) {
        // 获取path上模糊匹配到的变量
        LOG.info("paths: {}", request.paths());
        response.json(JSON.toJSONString(request.paths()));
    }

}
