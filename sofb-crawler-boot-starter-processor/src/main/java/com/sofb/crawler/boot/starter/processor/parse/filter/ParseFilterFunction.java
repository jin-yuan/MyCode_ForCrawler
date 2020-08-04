package com.sofb.crawler.boot.starter.processor.parse.filter;

import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-12-17 14:40
 */
@FunctionalInterface
public interface ParseFilterFunction {

  boolean parse(Response response, String pattern);
}
