package summer.http;

/**
 * http header Content-Type
 *
 * Created by xuan on 2018/4/26
 */
public enum ContentType {

    APPLICATION_JSON("application/json"),

    APPLICATION_JSON_UTF8("application/json;charset=utf-8"),

    TEXT_PLAIN("text/plain"),

    TEXT_PLAIN_UTF8("text/plain;charset=utf-8"),
    ;

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
