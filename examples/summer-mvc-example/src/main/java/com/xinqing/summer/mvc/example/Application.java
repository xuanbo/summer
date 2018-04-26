package com.xinqing.summer.mvc.example;

import com.xinqing.summer.mvc.Summers;
import com.xinqing.summer.mvc.example.controller.ExampleController;

/**
 * Hello world!
 */
public class Application {

    public static void main(String[] args) {
        ExampleController controller = new ExampleController();
        Summers.summer()
                .get("/", controller::text)
                // 输出文本
                .get("/example/text", controller::text)
                // 输出json
                .get("/example/json", controller::json)
                // 匹配路径参数id，要求:后面都是字母，否则当做精确匹配
                .get("/example/:id", controller::path)
                // 精确匹配/example/:11
                .get("/example/:11", controller::path)
                // 重定向
                .get("/example/redirect", controller::redirect)
                // 解析body，无法解析application/json
                .post("/example/body", controller::body)
                // 解析file
                .post("/example/file", controller::file)
                // 解析json
                .post("/example/parseJson", controller::parseJson)
                .listen(9000)
                .serve();
    }

}
