package com.sofb.crawler.boot.starter.processor.model.table;

import com.sofb.crawler.boot.starter.processor.model.CrawlerFilter;
import com.sofb.crawler.framework.core.model.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-22 17:34
 */
@Getter
@Setter
@Slf4j
public class TableResult {

  private TableArgs tableArgs;

  private Map<String, List<Map<String, String>>> items = new HashMap<>();

  private List<Request> newRequests = new ArrayList<>();

    private CrawlerFilter crawlerFilter;

    public static TableResult getInstance(String tableName, Map<String, String> colConfig, CrawlerFilter crawlerFilter) {

    TableResult tableResult = new TableResult();
    tableResult.tableArgs = TableArgs.getInstance(tableName, colConfig);
        tableResult.crawlerFilter = crawlerFilter;
    return tableResult;
  }

  /**
   * set 多条requests
   *
   * @param requests request list
   */
  public void setResult(List<Request> requests) {
    newRequests.addAll(requests);
  }

  /**
   * @param tableName 表名
   * @param itemsResult field
   */
  public void setResult(String tableName, Map<String, String> itemsResult) {
    if (items.get(tableName) == null) {
      items.put(
          tableName,
          new ArrayList<Map<String, String>>() {
            {
              add(itemsResult);
            }
          });
    } else {
      items.get(tableName).add(itemsResult);
    }
  }

  public void setResult(String tableName, List<Map<String, String>> itemResult) {

    if (CollectionUtils.isEmpty(itemResult)) {
      log.error("table {} 处理异常 itemResult 为空", tableName);
    }

    // 表 tableName 无累计数据，直接put 结果
    else if (CollectionUtils.isEmpty(items.get(tableName))) {
      items.put(tableName, itemResult);
    }
    // 表table和新item result 均为单条结果，直接取第零位置，加速处理
    else if (itemResult.size() == 1 && items.size() == 1) {
      items.get(tableName).get(0).putAll(itemResult.get(0));
    }
    // 新item result 为单条
    else if (itemResult.size() == 1) {
      items.get(tableName).forEach(m -> m.putAll(itemResult.get(0)));
    } else if (items.get(tableName).size() == 1) {
      itemResult.forEach(m -> m.putAll(items.get(tableName).get(0)));
      items.put(tableName, itemResult);
    } else if (items.get(tableName).size() > 1
        && items.get(tableName).size() == itemResult.size()) {
        for (int i = 0; i < items.get(tableName).size(); i++) {
            items.get(tableName).get(i).putAll(itemResult.get(i));
        }

    } else {
      log.info("table {} 无法处理item result {}", tableName, itemResult);
    }
  }

  public String getTableName() {
    return tableArgs.getTableName();
  }

  public Map<String, String> getColConfig() {
    return tableArgs.getColConfig();
  }
}
