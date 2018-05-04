package summer.json;

/**
 * Json Factory
 *
 * Created by xuan on 2018/4/26
 */
public class JsonFactory {

    private static final Json FAST_JSON = new FastJsonImpl();

    public static Json get() {
        return FAST_JSON;
    }

}
