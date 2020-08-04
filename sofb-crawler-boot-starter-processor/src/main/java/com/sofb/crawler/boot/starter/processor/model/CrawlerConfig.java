package com.sofb.crawler.boot.starter.processor.model;

import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.sofb.crawler.boot.starter.processor.constant.ProcessorConstant.DEFAULT_REGION_BEAN;

/**
 * 爬虫配置实体类
 *
 * @author liuxuejun
 * @date 2019-11-21 10:19
 */
@Data
public class CrawlerConfig {

  /** 爬虫配置 {platform：{house：{house-row:{title:aa=>args}}}} */
  private Map<String, Map<String, Map<String, Map<String, String>>>> config;

  /** 监听链 {platform:{key:chain}} */
  private Map<String, CrawlerListenerChain> listener;

  /** 过滤链 排除 {渠道：过滤} {渠道+消费类型：过滤} */
  private Map<String, CrawlerFilter> filter;

  /** 自定义页面解析任务 */
  private Map<String, Map<String, String>> region;

  Map<String, Map<String, String>> getCellConfig(String platform, String requestTaskType) {

    return config.get(platform).get(requestTaskType);
  }

  public Map<String, Map<String, String>> fetchCoreConfig(String platform, String requestType, String consumeType) {
    Map<String, Map<String, String>> result = new HashMap<>();
    if (config != null) {
      result = config.get(platform).get(requestType + "-" + consumeType);
      if (MapUtils.isEmpty(result)) {
        result = config.get(platform).get(requestType);
      }
    }
    return result;
  }

  /**
   * 获取监听器析配置方法
   *
   * @param platform 渠道
   * @param requestType 请求类型
   * @param consumeType 别名
   * @return 配置过滤链
   */
  public CrawlerListenerChain fetchListenerChain(
      String platform, String requestType, String consumeType) {

    if (listener != null) {
      CrawlerListenerChain  chain = new CrawlerListenerChain();
      if(!"delete".equals(consumeType)){
       chain = listener.get(platform + "-" + requestType);
      }
      if (StringUtils.isNotEmpty(consumeType)) {
        CrawlerListenerChain listenerChain =
            listener.get(platform + "-" + consumeType + "-" + requestType);
        if (listenerChain != null) {
          chain.mergeChain(listenerChain);
        }
          CrawlerListenerChain listenerChains = listener.get(consumeType + "-" + requestType);
          if (listenerChains != null) {
              chain.mergeChain(listenerChains);
          }
      }
      return chain;
    }
    return new CrawlerListenerChain();
  }

  /**
   * 获取过滤解析配置方法
   *
   * @param platform    渠道
   * @param requestType 请求类型
   * @param consumeType 别名
   * @return 配置过滤链
   */
  public CrawlerFilter fetchFilterConfig(
          String platform, String requestType, String consumeType) {
    if (filter != null) {
      return filter.get(platform + "-" + consumeType + "-" + requestType);
    }
    return null;
  }


  public String fetchRegionBean(String platform, String requestType) {

    return Optional.ofNullable(region)
        .map(m -> m.get(platform).get(requestType))
        .orElse(DEFAULT_REGION_BEAN);
  }
}
