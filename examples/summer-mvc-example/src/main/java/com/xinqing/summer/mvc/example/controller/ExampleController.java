package com.xinqing.summer.mvc.example.controller;

import com.alibaba.fastjson.JSON;
import com.xinqing.summer.mvc.domain.Result;
import com.xinqing.summer.mvc.http.Request;
import com.xinqing.summer.mvc.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

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
        response.json(Result.of("Hello World"));
    }

    public void path(Request request, Response response) {
        // 获取path上模糊匹配到的变量
        LOG.info("paths: {}", request.paths());
        response.json(JSON.toJSONString(request.paths()));
    }

    public void redirect(Request request, Response response) {
        response.redirect("/example/text");
    }

    public void body(Request request, Response response) {
        response.json(JSON.toJSONString(request.body()));
    }

    public void file(Request request, Response response) {
        request.file("file").forEach(fileUpload -> {
            LOG.info("{}", fileUpload.getFilename());
            try {
                // 保存文件到本地
                    fileUpload.renameTo(Paths.get("/file/" + fileUpload.getFilename()).toFile());
            } catch (IOException e) {
                LOG.warn("上传失败", e);
            }
        });
        response.text("done");
    }

    public void parseJson(Request request, Response response) {
        response.json(request.json(Example.class));
    }

    public void sendFile(Request request, Response response) {
        try {
            response.sendFile(Paths.get("/photo/test.jpg").toFile());
        } catch (IOException e) {
            LOG.error("send file error", e);
            response.text("send file error");
        }
    }
}

class Example {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
