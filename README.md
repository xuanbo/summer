## summer

### Usage

* quick start

```java
public class Application {

    public static void main(String[] args) {
        Summers.summer()
                .get("/index", (request, response) -> response.text("Hello World"))
                .get("/example/:id", (request, response) -> response.text(request.paths().get("id")))
                .listen(9000)
                .serve();
    }

}
```

## router

```java
public class Application {

    public static void main(String[] args) {
        ExampleController controller = new ExampleController();

        Summer summer = Summers.summer();

        // 获取路由对象
        Router router = summer.router();
        // 自定义notFound
        router.notFound((request, response) -> response.text("404"));
        // 注册路由
        router.get("/example/text", controller::text);
        router.get("/example/json", controller::json);

        // listenAndServe
        summer.listen(9000).serve();
    }

}
```