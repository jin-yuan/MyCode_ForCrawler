package com.sofb.crawler.boot.starter.processor.model.table;

import lombok.Getter;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-23 15:42
 */
@Getter
class TableArgs {

  private String tableName;

  private Map<String, String> colConfig;

  static TableArgs getInstance(String tableName, Map<String, String> colConfig) {
    TableArgs tableArgs = new TableArgs();
    tableArgs.tableName = tableName;
    tableArgs.colConfig = colConfig;

    return tableArgs;
  }
}
