package com.sofb.crawler.boot.starter.processor.parse;

import com.sofb.crawler.boot.starter.processor.model.CrawlerConfig;
import com.sofb.crawler.boot.starter.processor.model.CrawlerListenerChain;
import com.sofb.crawler.boot.starter.processor.model.region.RegionResult;
import com.sofb.crawler.boot.starter.processor.parse.filter.ParseFilterFunction;
import com.sofb.crawler.boot.starter.processor.parse.region.ParseRegionFunction;
import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.model.ResultItems;
import com.sofb.crawler.framework.core.model.SpiderContext;
import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuxuejun
 * @date 2019-11-23 13:15
 */
@Slf4j
public class CallBackFunctionManager {

  private Map<String, ParseRegionFunction> parseRegionFunctionMap;

  private Map<String, ParseFilterFunction> parseFilterFunctionMap;

  private CrawlerConfig crawlerConfig;

  public CallBackFunctionManager(
      Map<String, ParseRegionFunction> parseRegionFunctionMap,
      Map<String, ParseFilterFunction> parseFilterManagerMap,
      CrawlerConfig crawlerConfig) {
    this.parseRegionFunctionMap = parseRegionFunctionMap;
    this.crawlerConfig = crawlerConfig;
    this.parseFilterFunctionMap = parseFilterManagerMap;
  }

  public CallBackFunction defaultCallBack() {

    return (CallBackFunction & Serializable)
        (response) -> {
          SpiderContext spiderContext = SpiderContextThreadLocalStore.get();
          String consumeType =
              Optional.ofNullable(response.getExtra(RequestConstant.CONSUME_TYPE))
                  .orElse(RequestConstant.DEFAULT_CONSUME_TYPE)
                  .toString();
          String platform = spiderContext.getPlatformName();
          String requestType = response.getExtra(RequestConstant.TASK_TYPE).toString();


          CrawlerListenerChain listenerChain =
              Optional.ofNullable(
                      crawlerConfig.fetchListenerChain(platform, requestType, consumeType))
                  .orElse(new CrawlerListenerChain());
          log.info("处理 {} 请求开始", response.getUrl());

            RegionResult regionResult = RegionResult.getInstance(platform, requestType, consumeType);
          String regionBeanName = crawlerConfig.fetchRegionBean(platform, requestType);
          if (!processListener(response, listenerChain.getPre())&&!"delete".equals(consumeType)) {
            processResponse(response, regionBeanName, regionResult);
            processListener(response, listenerChain.getAfter());
            log.info("处理 {} 请求结束", response.getUrl());
          }
        };
  }

  private void processResponse(
      Response response, String regionBeanName, RegionResult regionResult) {

    ResultItems<List<Map<String, String>>> resultItems = response.getResultItems();
    parseRegionFunctionMap.get(regionBeanName).parse(response, regionResult);
    resultItems.addItems(regionResult.getAllItems());
    response.setResultItems(resultItems);
    response.setRequestList(regionResult.getAllRequests());
  }

  /**
   * @param response 响应体
   * @param filterMap 过滤map配置
   * @return true则拦截请求 false则不拦截
   */
  private boolean processListener(Response response, Map<String, String> filterMap) {
    AtomicBoolean ableEndTask = new AtomicBoolean(false);
    if (MapUtils.isEmpty(filterMap)) {
      return ableEndTask.get();
    }
    if (MapUtils.isNotEmpty(parseFilterFunctionMap)) {
      filterMap.forEach(
          (beanName, pattern) -> {
            ParseFilterFunction parseFilterFunction = parseFilterFunctionMap.get(beanName);
            if (parseFilterFunction != null && !ableEndTask.get()) {
              ableEndTask.set(parseFilterFunction.parse(response, pattern));
              if (ableEndTask.get()) {
                response.addMsg(String.format("请求 %s 被 %s拦截，将不予解析", response.getUrl(), beanName));
                response.setInvalidPage(true);

              }
            }
          });
    }
    return ableEndTask.get();
  }
}
