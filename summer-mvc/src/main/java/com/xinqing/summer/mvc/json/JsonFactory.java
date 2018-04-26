package com.xinqing.summer.mvc.json;

/**
 * Json Factory
 *
 * Created by xuan on 2018/4/26
 */
public class JsonFactory {

    private static final Json fastJson = new FastJsonImpl();

    public static Json get() {
        return fastJson;
    }

}
