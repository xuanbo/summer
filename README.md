# Summer Project

Summer is a simple mvc lib base on [Netty4.x](https://github.com/netty/netty) for **study!!!**

## Usage

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

### Request

#### Named parameters

As you can see, `:name` is a named parameter. You can get the value of a parameter by `request.paths().get("name")` method.

```
Pattern: /user/:name

/user/gordon              match
/user/you                 match
/user/gordon/profile      no match
/user/                    no match
```

**Note:** `:name` name must be [a-zA-Z], otherwise will be matched exactly!

#### Request body

method `request.body()` support `Content-Type`:

* multipart/form-data
* application/x-www-form-urlencoded

As for `application/json`, use method `request.json()` instead of.

#### Multipart file

* request.files()
* request.file(name)

Then you can use `FileUpload` for all operations.

### Response

#### Write response

* response.text(text)
* response.json(json)

`Content-Type`(`text/plain` and `application/json`) will be added.

#### File download

Now, easy to use:

* response.sendFile(file)
* response.sendFile(header, file)

#### Redirect

`response.redirect(targetUrl)` will set http status `302`, and add `Location` on http response header.

## Examples

[Here](https://github.com/xuanbo/summer/tree/master/examples)