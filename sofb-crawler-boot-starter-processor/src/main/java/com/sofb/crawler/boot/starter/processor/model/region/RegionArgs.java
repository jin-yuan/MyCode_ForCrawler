package com.sofb.crawler.boot.starter.processor.model.region;

import lombok.Getter;

/**
 * @author liuxuejun
 * @date 2019-11-23 15:53
 */
@Getter
class RegionArgs {

  private String platform;

  private String requestType;

    private String consumeType;

    static RegionArgs getInstance(String platform, String requestType, String consumeType) {

    RegionArgs regionArgs = new RegionArgs();
    regionArgs.platform = platform;
    regionArgs.requestType = requestType;
        regionArgs.consumeType = consumeType;

    return regionArgs;
  }
}
