package summer.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * PathMatcher 测试
 *
 * Created by xuan on 2018/4/25
 */
public class PathMatcherTest {

    @Test
    public void isPattern() {
        Assert.assertFalse(PathMatcher.isPattern("/a/b/"));
        Assert.assertFalse(PathMatcher.isPattern("/a/b"));
        Assert.assertFalse(PathMatcher.isPattern("/a/"));
        Assert.assertFalse(PathMatcher.isPattern("/a"));
        Assert.assertFalse(PathMatcher.isPattern("/"));
        Assert.assertFalse(PathMatcher.isPattern(""));

        Assert.assertTrue(PathMatcher.isPattern("/a/:b/:11"));
        Assert.assertTrue(PathMatcher.isPattern("/a/:b/: "));
        Assert.assertTrue(PathMatcher.isPattern("/a/:b/"));
        Assert.assertTrue(PathMatcher.isPattern("/a/:bb"));
        Assert.assertFalse(PathMatcher.isPattern("/a/:"));
        Assert.assertFalse(PathMatcher.isPattern("/a/"));
        Assert.assertFalse(PathMatcher.isPattern("/a"));
    }

    @Test
    public void toRegex() {
        System.out.println(PathMatcher.toRegex("/a/:b/"));
        System.out.println(PathMatcher.toRegex("/a/:b"));
        System.out.println(PathMatcher.toRegex("/a/:"));
        System.out.println(PathMatcher.toRegex("/a/"));
        System.out.println(PathMatcher.toRegex("/a"));
        System.out.println(PathMatcher.toRegex("/"));
        System.out.println(PathMatcher.toRegex(""));
    }

    @Test
    public void match() {
        String[] match1 = PathMatcher.match("^\\/user\\/([^\\/]+)\\/([^\\/]+)$", "/user/1/vv");
        System.out.println(Arrays.toString(match1));

        String[] match2 = PathMatcher.match("^\\/user\\/([^\\/]+)\\/([^\\/]+)$", "/user/:/:name");
        System.out.println(Arrays.toString(match2));
    }

}
