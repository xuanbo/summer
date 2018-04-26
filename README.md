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

* custom

```java
public class Application {

    public static void main(String[] args) {
        ExampleController controller = new ExampleController();

        Summer summer = Summers.summer();

        Router router = summer.router();

        // custom notFound handler
        router.notFound((request, response) -> response.text("404"));

        // custom failure handler
        router.failureHandler((request, response, t) -> response.text("500"));

        // register routes
        router.get("/example/text", controller::text);
        router.get("/example/json", controller::json);

        // http server listen on 9000 and serve
        summer.listen(9000).serve();
    }

}
```

## About

### request

* uri
* path
* queryString
* parameters
* path variables
* http method
* http header
* http body
* http body json
* multipart file

### response

* http header
* http status
* send text
* send json
* redirect

## Examples

[Here](https://github.com/xuanbo/summer/tree/master/examples)