package summer.http.handler;

import summer.http.Request;
import summer.http.Response;

/**
 * http handler
 *
 * Created by xuan on 2018/4/17
 */
@FunctionalInterface
public interface Handler {

    /**
     * do handle
     *
     * @param request http request
     * @param response http response
     */
    void handle(Request request, Response response);

}
