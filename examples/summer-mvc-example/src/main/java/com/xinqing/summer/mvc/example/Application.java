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
                .get("/example/text", controller::text)
                .get("/example/json", controller::json)
                .listen(9000)
                .serve();
    }

}
