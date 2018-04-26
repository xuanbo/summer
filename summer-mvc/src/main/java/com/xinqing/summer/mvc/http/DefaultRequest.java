package com.xinqing.summer.mvc.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * default http request
 *
 * Created by xuan on 2018/4/17
 */
public class DefaultRequest implements Request {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRequest.class);

    private static final char QUESTION = '?';
    private static final char AND = '&';
    private static final char EQUAL = '=';

    private final FullHttpRequest raw;

    private String uri;
    private String path;
    private String queryString;
    private Map<String, List<String>> params;
    private Map<String, String> paths;

    public DefaultRequest(FullHttpRequest raw) {
        this.raw = raw;
    }

    @Override
    public String uri() {
        if (uri == null) {
            try {
                uri = URLDecoder.decode(raw.uri(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                uri = raw.uri();
                LOG.debug("uri decode encoding unsupported");
            }
        }
        return uri;
    }

    @Override
    public String path() {
        if (path == null) {
            String uri = uri();
            int indexOf = StringUtils.indexOf(uri, QUESTION);
            if (indexOf == -1) {
                path = uri;
            } else {
                path = StringUtils.substring(uri, 0, indexOf);
            }
        }
        return path;
    }

    @Override
    public Map<String, String> paths() {
        if (paths == null) {
            return Collections.emptyMap();
        }
        return paths;
    }

    @Override
    public void paths(Map<String, String> paths) {
        this.paths = paths;
    }

    @Override
    public String queryString() {
        if (queryString == null) {
            String uri = uri();
            int indexOf = StringUtils.indexOf(uri, QUESTION);
            if (indexOf == -1) {
                queryString = StringUtils.EMPTY;
            } else {
                queryString = StringUtils.substring(uri, indexOf + 1);
            }
        }
        return queryString;
    }

    @Override
    public Map<String, List<String>> params() {
        if (params == null) {
            String queryString = queryString();
            if (StringUtils.isEmpty(queryString)) {
                params = Collections.emptyMap();
                return params;
            }
            // 获取路径上'&'分割的每一对参数
            List<String[]> pairs = Arrays.stream(StringUtils.split(queryString, AND))
                    .map(parameter -> StringUtils.split(parameter, EQUAL))
                    .filter(pair -> pair.length == 2)
                    .collect(Collectors.toList());
            params = new HashMap<>(pairs.size());
            for (String[] pair : pairs) {
                params.computeIfAbsent(pair[0], v -> new ArrayList<>()).add(pair[1]);
            }
            return params;
        }
        return params;
    }

    @Override
    public String param(String name) {
        List<String> values = params().get(name);
        if (values == null) {
            return null;
        }
        return values.get(0);
    }

    @Override
    public List<String> paramValues(String name) {
        return params().getOrDefault(name, Collections.emptyList());
    }

    @Override
    public HttpMethod method() {
        return raw.method();
    }

    @Override
    public HttpHeaders headers() {
        return raw.headers();
    }

    @Override
    public String header(String header) {
        return headers().get(header);
    }
}
