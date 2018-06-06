package com.xinqing.summer.mvc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 路径匹配
 *
 * Created by xuan on 2018/4/25
 */
public final class PathMatcher {

    /**
     * 模式匹配：/user/:variable，其中variable必须为纯字母，否则作为精确匹配
     */
    private static final Pattern PATTERN = Pattern.compile("(/:[a-zA-Z]+)");

    private PathMatcher() {
    }

    /**
     * 路径是否为模式匹配
     *
     * @param path 例如：/user/:id, /user/uua
     * @return 精确匹配
     */
    public static boolean isPattern(String path) {
        Matcher matcher = PATTERN.matcher(path);
        return matcher.find();
    }

    /**
     * 将模式匹配路径替换为正则
     * /user/:name -> ^\/user\/([^\/]+)\/([^\/]+)$
     *
     * @param pattern /user/:name
     * @return ^\/user\/([^\/]+)\/([^\/]+)$
     */
    public static String toRegex(String pattern) {
        if (isPattern(pattern)) {
            String regex = pattern.replaceAll(":[a-zA-Z]+", "([^\\/]+)");
            return "^\\" + regex + "*$";
        }
        return pattern;
    }

    /**
     * 将模式匹配路径替换为ant风格
     * /user/:name -> /user/*
     *
     * @param pattern /user/:name
     * @return /user/*
     */
    public static String toAnt(String pattern) {
        if (isPattern(pattern)) {
            return pattern.replaceAll(":[a-zA-Z]+", "*");
        }
        return pattern;
    }

    /**
     * 提取正则匹配参数
     *
     * @param regex ^\/user\/([^\/]+)\/([^\/]+)$
     * @param path /user/1/zhangsan
     * @return [1, zhangsan]
     */
    public static String[] match(String regex, String path) {
        return match(Pattern.compile(regex), path);
    }

    public static String[] match(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            int count = matcher.groupCount();
            String[] paths = new String[count];
            for (int i = 0; i < count; i++) {
                String group = matcher.group(i + 1);
                paths[i] = group;
            }
            return paths;
        }
        return new String[]{};
    }

}
