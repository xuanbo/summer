package com.xinqing.summer.mvc.http;

import com.xinqing.summer.mvc.exception.BodyException;
import com.xinqing.summer.mvc.json.JsonFactory;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    /**
     * 请求路径上queryString中提取的参数
     */
    private Map<String, List<String>> params;

    /**
     * 模式匹配到的路径参数
     */
    private Map<String, String> paths;

    /**
     * request body提取的参数，无法提取文件
     */
    private Map<String, String> body;

    /**
     * request body中提取的文件对象
     */
    private Map<String, List<FileUpload>> fileUploads;

    DefaultRequest(FullHttpRequest raw) {
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

    @Override
    public ByteBuf content() {
        return raw.content();
    }

    @Override
    public Map<String, String> body() {
        if (body == null) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(raw);
            List<InterfaceHttpData> data = decoder.getBodyHttpDatas();
            body = new HashMap<>(data.size());
            for (InterfaceHttpData httpData : data) {
                String name = httpData.getName();
                if (InterfaceHttpData.HttpDataType.Attribute == httpData.getHttpDataType()) {
                    Attribute attribute = (Attribute) httpData;
                    try {
                        // 这里会覆盖相同的name
                        body.put(name, attribute.getValue());
                    } catch (IOException e) {
                        throw new BodyException("parse body error", e);
                    }
                }
            }
        }
        return body;
    }

    @Override
    public <T> T json(Class<T> requireClazz) {
        if (isApplicationJson()) {
            ByteBuf byteBuf = content();
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            return JsonFactory.get().parseJson(bytes, requireClazz);
        }
        return null;
    }

    @Override
    public Map<String, List<FileUpload>> files() {
        if (fileUploads == null) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(raw);
            List<InterfaceHttpData> data = decoder.getBodyHttpDatas();
            if (decoder.isMultipart()) {
                body = new HashMap<>(data.size());
                fileUploads = new HashMap<>(data.size());
                for (InterfaceHttpData httpData : data) {
                    String name = httpData.getName();
                    if (InterfaceHttpData.HttpDataType.Attribute == httpData.getHttpDataType()) {
                        Attribute attribute = (Attribute) httpData;
                        try {
                            // 这里会覆盖相同的name
                            body.put(name, attribute.getValue());
                        } catch (IOException e) {
                            throw new BodyException("parse body error", e);
                        }
                    }
                    if (InterfaceHttpData.HttpDataType.FileUpload == httpData.getHttpDataType()) {
                        FileUpload fileUpload = (FileUpload) httpData;
                        fileUploads.computeIfAbsent(name, v -> new ArrayList<>()).add(fileUpload);
                    }
                }
            } else {
                fileUploads = Collections.emptyMap();
            }
        }
        return fileUploads;
    }

    @Override
    public List<FileUpload> file(String name) {
        return files().getOrDefault(name, Collections.emptyList());
    }

    private boolean isApplicationJson() {
        return HttpHeaderValues.APPLICATION_JSON.toString().equals(header(HttpHeaderNames.CONTENT_TYPE.toString()));
    }
}
