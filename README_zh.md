# Summer Project

Summer是一个用于**学习交流**，基于[Netty4.x](https://github.com/netty/netty)的简单mvc库

## 使用

* 快速开始

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

* 自定义

```java
public class Application {

    public static void main(String[] args) {
        ExampleController controller = new ExampleController();

        // 获取一个summer实例
        Summer summer = Summer.me();

        Router router = summer.router();

        // 自定义notFound处理
        router.notFound((request, response) -> response.text("404"));

        // 自定义错误处理
        router.failureHandler((request, response, t) -> response.text("500"));

        // 注册路由
        router.get("/example/text", controller::text);
        router.get("/example/json", controller::json);

        // http服务监听9000端口，并启动服务
        summer.listen(9000).serve();
    }

}
```

## 关于

### Request

#### 命名参数

显然，`:name`就是一个命名参数，可以通过`request.paths().get("name")`方法获取命名参数。

```
模式匹配: /user/:name

/user/zhangsan             匹配
/user/lisi                 匹配
/user/wangwu/zhaoliu       不匹配
/user/                     不匹配
```

**注意:** `:name` name 必须为字母[a-zA-Z], 否则视为精确匹配!

#### 前置钩子

Ant风格:

* `?` 匹配一个字符
* `*` 匹配一个或多个字符
* `**` 匹配一个或多个目录

#### 请求体

`request.body()`方法支持下列`Content-Type`:

* `multipart/form-data`
* `application/x-www-form-urlencoded`

对于`application/json`请求头, 使用`request.json()`方法即可。

#### 文件上传

* `request.files()`
* `request.file(name)`

拿到`FileUpload`对象，操作文件。

### Response

#### 写响应

* `response.text(text)`
* `response.json(json)`

`Content-Type`(`text/plain`和`application/json`)分别会被添加到响应头。

#### 文件下载

使用下面的方法:

* `response.sendFile(file)`

#### 重定向

`response.redirect(targetUrl)`将会设置http状态码为`302`，并添加`Location`到响应头。

#### 静态资源

```
Summers.summer()
        // 静态资源
        .staticFile("/static", "/developer/Code/summer")
        .listen(9000)
        .serve();
```

例如，`http://ip:9000/static/some.txt`将会被映射为本地文件路径`/developer/Code/summer/some.txt`

## 例子

[这里](https://github.com/xuanbo/summer/tree/master/examples)

## 特别感谢

* [netty](https://github.com/netty/netty)
* [spring-framework](https://github.com/spring-projects/spring-framework)
* [blade](https://github.com/lets-blade/blade)
* [shiro](https://github.com/apache/shiro)
* [motan](https://github.com/weibocom/motan)