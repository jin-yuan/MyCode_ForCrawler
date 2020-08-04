package com.sofb.crawler.boot.starter.processor.parse.region;

import com.sofb.crawler.boot.starter.processor.model.region.RegionResult;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-11-23 11:03
 */
@FunctionalInterface
public interface ParseRegionFunction {

  void parse(Response response, RegionResult regionResult);
}
