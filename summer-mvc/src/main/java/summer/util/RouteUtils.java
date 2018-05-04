package summer.util;

import summer.exception.RouteException;
import org.apache.commons.lang3.StringUtils;

/**
 * route utils
 *
 * Created by xuan on 2018/4/28
 */
public final class RouteUtils {

    public static final String PATH_START = "/";

    private RouteUtils() {
    }

    /**
     * 检查请求路径是否合法，必须以'/'开头，且不能以'/'结尾
     * 例如：'/a/b'是合法的；'/a/b/'不合法
     * 特别地：'/'是合法的
     *
     * @param path 请求路径
     */
    public static void checkPath(String path) {
        // 必须以'/'开头
        if (!StringUtils.startsWith(path, PATH_START)) {
            throw new RouteException("path '" + path + "' must start with: " + PATH_START);
        }
        // 不能以'/'结尾
        if (StringUtils.length(path) > PATH_START.length() && StringUtils.endsWith(path, PATH_START)) {
            throw new RouteException("path '" + path + "' should not end with: " + PATH_START);
        }
    }

}
