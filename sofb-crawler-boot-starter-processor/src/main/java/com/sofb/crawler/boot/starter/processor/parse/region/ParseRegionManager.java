package com.sofb.crawler.boot.starter.processor.parse.region;

import com.sofb.crawler.boot.starter.processor.constant.ProcessorConstant;
import com.sofb.crawler.boot.starter.processor.model.CrawlerConfig;
import com.sofb.crawler.boot.starter.processor.model.CrawlerFilter;
import com.sofb.crawler.boot.starter.processor.model.table.TableResult;
import com.sofb.crawler.boot.starter.processor.parse.table.ParseTableFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-23 11:05
 */
@Slf4j
public class ParseRegionManager {

  private Map<String, ParseTableFunction> parseTableFunctionMap;
  private CrawlerConfig crawlerConfig;

  public ParseRegionManager(
      Map<String, ParseTableFunction> parseTableFunctionMap, CrawlerConfig crawlerConfig) {
    this.parseTableFunctionMap = parseTableFunctionMap;
    this.crawlerConfig = crawlerConfig;
  }

  public ParseRegionFunction defaultRegion() {

      return (response, regionResult) -> {
          CrawlerFilter crawlerFilter = crawlerConfig.fetchFilterConfig(regionResult.getPlatform(), regionResult.getRequestType(), regionResult.getConsumeType());
        crawlerConfig
                .fetchCoreConfig(regionResult.getPlatform(), regionResult.getRequestType(), regionResult.getConsumeType())
            .forEach(
                (key, rowConfig) -> {
                  String name = key.split(ProcessorConstant.ITEM_SPITE_PATTERN)[0];
                  String tableBeanName = key.split(ProcessorConstant.ITEM_SPITE_PATTERN)[1];

                    TableResult tableResult = TableResult.getInstance(name, rowConfig, crawlerFilter);

                  parseTableFunctionMap.get(tableBeanName).parse(response, tableResult);

                  regionResult.merge(tableResult);
                });
      };
  }
}
