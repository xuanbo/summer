# Summer Project

Summer is a simple mvc lib base on [Netty4.x](https://github.com/netty/netty) for **study!!!**

## Usage

* quick start

```java
public class Application {

    public static void main(String[] args) {
        Summer.me()
                .before("/example/*", (request, response) -> {
                    log.debug("path: {}", request.path());
                    // pass
                    return true;
                })
                .get("/example", (request, response) -> response.json(Result.of("summer *_*!!!")))
                .get("/example/:id", (request, response) -> response.text(request.paths().get("id")))
                .post("/example/:id", (request, response) -> response.text(request.paths().get("id")))
                .put("/example/:id", (request, response) -> response.text(request.paths().get("id")))
                .delete("/example/:id", (request, response) -> response.text(request.paths().get("id")))
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

        // summer instance
        Summer summer = Summer.me();

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

### Request

#### Named parameters

As you can see, `:name` is a named parameter. You can get the value of a parameter by `request.paths().get("name")` method.

```
Pattern: /user/:name

/user/zhangsan            match
/user/lisi                match
/user/wangwu/zhaoliu      no match
/user/                    no match
```

**Note:** `:name` name must be [a-zA-Z], otherwise will be matched exactly!

#### Before hook

Ant pattern:

* `?` match a character
* `*` match one or more characters
* `**` match one or more directories

#### Request body

method `request.body()` support `Content-Type`:

* `multipart/form-data`
* `application/x-www-form-urlencoded`

As for `application/json`, use method `request.json()` instead of.

#### Multipart file

* `request.files()`
* `request.file(name)`

Then you can use `FileUpload` for all operations.

### Response

#### Write response

* `response.text(text)`
* `response.json(json)`

`Content-Type`(`text/plain` and `application/json`) will be added.

#### File download

Now, easy to use:

* `response.sendFile(file)`

#### Redirect

`response.redirect(targetUrl)` will set http status `302`, and add `Location` on http response header.

#### Static resource

```
Summers.summer()
        // static resource
        .staticFile("/static", "/developer/Code/summer")
        .listen(9000)
        .serve();
```

For example, request `http://ip:9000/static/some.txt` will be mapped to the local file path: `/developer/Code/summer/some.txt`

## Examples

[Here](https://github.com/xuanbo/summer/tree/master/examples)

## Thanks

* [netty](https://github.com/netty/netty)
* [spring-framework](https://github.com/spring-projects/spring-framework)
* [blade](https://github.com/lets-blade/blade)
* [shiro](https://github.com/apache/shiro)
* [motan](https://github.com/weibocom/motan)