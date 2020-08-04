package com.sofb.crawler.boot.starter.processor.config;

import com.sofb.crawler.boot.starter.processor.model.CrawlerConfig;
import com.sofb.crawler.boot.starter.processor.parse.CallBackFunctionManager;
import com.sofb.crawler.boot.starter.processor.parse.filter.ParseFilterFunction;
import com.sofb.crawler.boot.starter.processor.parse.region.ParseRegionFunction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-21 18:06
 */
@Configuration
public class EnableAutoCrawlerConfig {

  @ConditionalOnMissingBean(CrawlerConfig.class)
  @Bean
  @ConfigurationProperties(prefix = "sofb.crawler.processor")
  CrawlerConfig crawlerConfig() {
    return new CrawlerConfig();
  }

  @Bean
  CallBackFunctionManager callBackFunctionManager(
      Map<String, ParseRegionFunction> parseRegionFunctionMap,
      Map<String, ParseFilterFunction> parseFilterFunctionMap,
      CrawlerConfig crawlerConfig) {
    return new CallBackFunctionManager(
        parseRegionFunctionMap, parseFilterFunctionMap, crawlerConfig);
  }
}
