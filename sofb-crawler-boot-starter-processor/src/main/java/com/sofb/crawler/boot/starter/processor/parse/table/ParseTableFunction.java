package com.sofb.crawler.boot.starter.processor.parse.table;

import com.sofb.crawler.boot.starter.processor.model.table.TableResult;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-11-22 15:27
 */
@FunctionalInterface
public interface ParseTableFunction {

  void parse(Response response, TableResult tableResult);
}
