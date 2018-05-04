package summer.http;

/**
 * request context
 *
 * Created by xuan on 2018/4/26
 */
public class RequestContext {

    private static final ThreadLocal<Request> REQUEST_THREAD_LOCAL = new InheritableThreadLocal<>();

    static void setup(Request request) {
        REQUEST_THREAD_LOCAL.set(request);
    }

    public static Request current() {
        return REQUEST_THREAD_LOCAL.get();
    }

    static void cleanup() {
        REQUEST_THREAD_LOCAL.remove();
    }

}
