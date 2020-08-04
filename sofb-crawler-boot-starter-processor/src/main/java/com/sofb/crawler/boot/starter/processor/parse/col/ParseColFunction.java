package com.sofb.crawler.boot.starter.processor.parse.col;

import com.sofb.crawler.boot.starter.processor.model.col.ColResult;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-11-22 15:27
 */
@FunctionalInterface
public interface ParseColFunction {

  /**
   * 解析配置最基本单位 列族
   *
   * @param response 响应体
   * @param colResult 解析结果
   */
  void parse(Response response, ColResult colResult);
}
