## summer

### Usage

```java
public class Application {

    public static void main(String[] args) {
        Summers.summer()
                .get("/index", (request, response) -> response.text("Hello World"))
                .listen(9000)
                .serve();
    }

}
```