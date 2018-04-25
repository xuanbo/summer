package com.xinqing.summer.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * summers
 *
 * Created by xuan on 2018/4/25
 */
public final class Summers {

    private static final Logger LOG = LoggerFactory.getLogger(Summers.class);

    private static final Summer SUMMER = newInstance();

    private Summers() {
    }

    public static Summer summer() {
        return SUMMER;
    }

    private static Summer newInstance() {
        LOG.info("*_*!!!");
        return new Summer();
    }

}
