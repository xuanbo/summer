package com.xinqing.summer.mvc.json;

/**
 * json interface
 *
 * Created by xuan on 2018/4/26
 */
public interface Json {

    /**
     * 将对象序列化json string
     *
     * @param obj 对象
     * @param <T> T
     * @return T
     */
    <T> String toJson(T obj);

    /**
     * 将json string解析为对象
     *
     * @param json json string
     * @param requireClazz 需要的对象
     * @param <T> T
     * @return T
     */
    <T> T parseJson(String json, Class<T> requireClazz);

    /**
     * 将json字节数组解析为对象
     *
     * @param json json byte[]
     * @param requireClazz 需要的对象
     * @param <T> T
     * @return T
     */
    <T> T parseJson(byte[] json, Class<T> requireClazz);

}
