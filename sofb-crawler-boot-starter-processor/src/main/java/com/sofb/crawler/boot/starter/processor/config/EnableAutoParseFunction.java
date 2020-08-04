package com.sofb.crawler.boot.starter.processor.config;

import com.sofb.crawler.boot.starter.processor.constant.ProcessorConstant;
import com.sofb.crawler.boot.starter.processor.model.CrawlerConfig;
import com.sofb.crawler.boot.starter.processor.parse.CallBackFunctionManager;
import com.sofb.crawler.boot.starter.processor.parse.col.ParseColFunction;
import com.sofb.crawler.boot.starter.processor.parse.col.ParseColManager;
import com.sofb.crawler.boot.starter.processor.parse.filter.ParseFilterFunction;
import com.sofb.crawler.boot.starter.processor.parse.filter.ParseFilterManager;
import com.sofb.crawler.boot.starter.processor.parse.region.ParseRegionFunction;
import com.sofb.crawler.boot.starter.processor.parse.region.ParseRegionManager;
import com.sofb.crawler.boot.starter.processor.parse.table.ParseTableFunction;
import com.sofb.crawler.boot.starter.processor.parse.table.ParseTableManager;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-23 13:45
 */
@Configuration
public class EnableAutoParseFunction {

  @Bean(ProcessorConstant.FIELD_TYPE_XPATH)
  public ParseColFunction xpath() {
    return new ParseColManager().xpath();
  }

  @Bean(ProcessorConstant.FIELD_TYPE_CONSTANT)
  public ParseColFunction constant() {
    return new ParseColManager().constant();
  }

  @Bean(ProcessorConstant.FIELD_TYPE_REQUEST_EXTRAS)
  public ParseColFunction requestExtra() {
    return new ParseColManager().requestExtra();
  }

  @Bean(ProcessorConstant.ITEM_TYPE_ITEM)
  public ParseTableFunction item(Map<String, ParseColFunction> parseColFunctionMap) {
    return new ParseTableManager(parseColFunctionMap).item();
  }

  @Bean(ProcessorConstant.ITEM_TYPE_LINK)
  public ParseTableFunction link(Map<String, ParseColFunction> parseColFunctionMap) {
    return new ParseTableManager(parseColFunctionMap).link();
  }

  @Bean(ProcessorConstant.DEFAULT_REGION_BEAN)
  public ParseRegionFunction defaultRegionManager(
      Map<String, ParseTableFunction> parseTableFunctionMap, CrawlerConfig crawlerConfig) {
    return new ParseRegionManager(parseTableFunctionMap, crawlerConfig).defaultRegion();
  }

  @Bean(ProcessorConstant.DEFAULT_CALLBACK_BEAN)
  public CallBackFunction defaultCallBackManager(
      Map<String, ParseRegionFunction> parseRegionFunctionMap,
      Map<String, ParseFilterFunction> parseFilterFunctionMap,
      CrawlerConfig crawlerConfig) {
    return new CallBackFunctionManager(
            parseRegionFunctionMap, parseFilterFunctionMap, crawlerConfig)
        .defaultCallBack();
  }

  @Bean(ProcessorConstant.ADD_EXTRA_WITH_XPATH)
  public ParseFilterFunction addExtraWithXpath() {

    return new ParseFilterManager().addExtraWithXpath();
  }

  @Bean
  public ParseFilterFunction isSaleOut() {
    return new ParseFilterManager().isSaleOut();
  }
}
