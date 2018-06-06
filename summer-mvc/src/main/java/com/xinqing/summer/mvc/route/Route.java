package com.xinqing.summer.mvc.route;

import com.xinqing.summer.mvc.util.PathMatcher;
import com.xinqing.summer.mvc.http.handler.Handler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Route
 *
 * Created by xuan on 2018/4/25
 */
public class Route {

    /**
     * 请求路径
     */
    private String path;

    /**
     * http method，例如GET、POST等
     */
    private Set<HttpMethod> methods;

    /**
     * http handler
     */
    private Handler handler;

    /**
     * 是否精确匹配
     */
    private boolean exactPath;

    /**
     * 精确匹配时，替换为正则
     */
    private String regex;

    /**
     * 精确匹配时，替换为正则
     */
    private Pattern pattern;

    /**
     * 模式匹配提取的变量
     * 例如：/a/:b/c则提取{b}
     */
    private String[] variables;

    /**
     * 精确匹配时，替换为ant风格
     */
    private String ant;

    public Route(String path, Set<HttpMethod> methods, Handler handler) {
        this.path = path;
        this.methods = methods;
        this.handler = handler;
        checkPatternPath();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<HttpMethod> getMethods() {
        return methods;
    }

    public void setMethods(Set<HttpMethod> methods) {
        this.methods = methods;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isExactPath() {
        return exactPath;
    }

    public void setExactPath(boolean exactPath) {
        this.exactPath = exactPath;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String[] getVariables() {
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public String getAnt() {
        return ant;
    }

    public void setAnt(String ant) {
        this.ant = ant;
    }

    private void checkPatternPath() {
        // 如果为模式匹配，则替换为正则
        boolean isPattern = PathMatcher.isPattern(path);
        if (isPattern) {
            // 模式匹配替换为正则
            regex = PathMatcher.toRegex(path);
            pattern = Pattern.compile(regex);
            // 提取模式匹配路径上的变量
            extractVariables();
            ant = PathMatcher.toAnt(path);
        }
        exactPath = !isPattern;
    }

    private void extractVariables() {
        // 去掉':'前缀
        String[] variables = PathMatcher.match(regex, path);
        int len = variables.length;
        this.variables = new String[len];
        for (int i = 0; i < len; i++) {
            this.variables[i] = variables[i].substring(1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        return Objects.equals(path, route.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
