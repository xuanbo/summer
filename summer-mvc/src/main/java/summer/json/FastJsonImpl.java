package summer.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import summer.exception.JsonException;

/**
 * fastjson实现
 *
 * Created by xuan on 2018/4/26
 */
public class FastJsonImpl implements Json {

    @Override
    public <T> String toJson(T obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (JSONException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parseJson(String json, Class<T> requireClazz) {
        try {
            return JSON.parseObject(json, requireClazz);
        } catch (JSONException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parseJson(byte[] json, Class<T> requireClazz) {
        try {
            return JSON.parseObject(json, requireClazz);
        } catch (JSONException e) {
            throw new JsonException(e);
        }
    }
}
