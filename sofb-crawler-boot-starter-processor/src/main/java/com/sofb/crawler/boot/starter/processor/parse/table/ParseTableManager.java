package com.sofb.crawler.boot.starter.processor.parse.table;

import com.sofb.crawler.boot.starter.processor.constant.ProcessorConstant;
import com.sofb.crawler.boot.starter.processor.model.CrawlerFilter;
import com.sofb.crawler.boot.starter.processor.model.col.ColResult;
import com.sofb.crawler.boot.starter.processor.parse.col.ParseColFunction;
import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.model.SpiderContext;
import com.sofb.crawler.framework.core.util.GenRequestFromUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuxuejun
 * @date 2019-11-22 15:57
 */
@Slf4j
public class ParseTableManager {

  private Map<String, ParseColFunction> parseColFunctionMap;

  public ParseTableManager(Map<String, ParseColFunction> parseColFunctionMap) {
    this.parseColFunctionMap = parseColFunctionMap;
  }

  public ParseTableFunction item() {

    return (response, tableResult) -> {
      String tableName = tableResult.getTableName();
      if (isNeedParseTable(tableResult.getCrawlerFilter(), tableName)) {
        log.info("开始处理 表级任务 {}", tableName);
      } else {
        log.info("表级任务 {} 类型不匹配，丢弃任务", tableName);
        return;
      }
      for (Map.Entry<String, String> entry : tableResult.getColConfig().entrySet()) {

        tableResult.setResult(tableName, getColResult(entry, response).all());
      }
    };
  }

  public ParseTableFunction link() {
    return (response, tableResult) -> {
      String taskType = tableResult.getTableName();
      if (isNeedParseTaskType(tableResult.getCrawlerFilter(), taskType)) {
        log.info("开始处理请求级任务 {}", taskType);
      } else {
        log.info("请求级任务 {} 类型不匹配，丢弃任务", taskType);
        return;
      }
      ColResult requestCellResult = null;
      Map<String, Object> extras = new HashMap<>();
      for (Map.Entry<String, String> entry : tableResult.getColConfig().entrySet()) {
        ColResult colResult = getColResult(entry, response);
        if ("request".equals(colResult.getFieldName())) {
          requestCellResult = SerializationUtils.clone(colResult);

        } else {
          extras.putAll(colResult.get());
        }
      }

      if (requestCellResult != null) {
        tableResult.setResult(
                requestCellResult.allCleanResult().stream().filter(StringUtils::isNotBlank).filter(m -> !m.contains("javascript"))
                .map(
                    url ->
                        GenRequestFromUrl.addRequest(
                            url, tableResult.getTableName(), extras, response))
                .collect(Collectors.toList()));
      }
    };
  }

  public ColResult getColResult(Map.Entry<String, String> entry, Response response) {

    String key = entry.getKey();
    String value = entry.getValue();
    String name = key.split(ProcessorConstant.ITEM_SPITE_PATTERN)[0];
    String cellBeanName = key.split(ProcessorConstant.ITEM_SPITE_PATTERN)[1];
    ColResult cellResult = ColResult.getInstance(name, value);
    try {
      parseColFunctionMap.get(cellBeanName).parse(response, cellResult);
    } catch (RuntimeException e) {
      response.setInvalidPage(true);
      log.info("解析字段 {},pattern {},相应文本 {} ，发生异常 {}",key,value,Optional.ofNullable(response.getText()).orElse("").substring(0,1000),e);
      response.addMsg(String.format("解析字段 field %s ，pattern %s 发生异常 %s", key, value, e));
      throw new CrawlerException(CrawlerErrorCodeEnum.ANTI_CRAWLER_ERROR, e);
    }

    return cellResult;
  }

  public boolean isNeedParseTable(CrawlerFilter crawlerFilter, String tableName) {
    boolean isNeed = true;
    if (crawlerFilter != null) {
      String includeTablesByConfig =
              crawlerFilter.getIncludeTables();
      String excludeTablesByConfig =
              crawlerFilter.getExcludeTables();

      isNeed =
              (StringUtils.isEmpty(includeTablesByConfig) || new HashSet<>(Arrays.asList(includeTablesByConfig.split(","))).contains(tableName))
                      && (StringUtils.isEmpty(excludeTablesByConfig) || !new HashSet<>(Arrays.asList(excludeTablesByConfig.split(",")))
                      .contains(tableName));
    }


    if (isNeed) {
      SpiderContext spiderContext = SpiderContextThreadLocalStore.get();
      String[] includeTables = spiderContext.getIncludeTables();
      String[] excludeTables = spiderContext.getExcludeTables();
      isNeed = (ArrayUtils.isEmpty(includeTables) ||
              new HashSet<>(Arrays.asList(includeTables)).contains(tableName))
              && (ArrayUtils.isEmpty(excludeTables) || !new HashSet<>(Arrays.asList(excludeTables)).contains(tableName));
    }
    return isNeed;


  }


  public boolean isNeedParseTaskType(CrawlerFilter crawlerFilter, String taskType) {
    boolean isNeed = true;
    if (crawlerFilter != null) {
      String includeTypesByConfig =
              Optional.of(crawlerFilter.getIncludeTypes()).orElse(StringUtils.EMPTY);
      String excludeTypesByConfig =
              Optional.of(crawlerFilter.getExcludeTypes()).orElse(StringUtils.EMPTY);

      isNeed = (StringUtils.isEmpty(includeTypesByConfig) ||
              (new HashSet<>(Arrays.asList(includeTypesByConfig.split(","))).contains(taskType)))
              && (StringUtils.isEmpty(excludeTypesByConfig) || !new HashSet<>(Arrays.asList(excludeTypesByConfig.split(",")))
              .contains(taskType));
    }
    if (isNeed) {
      SpiderContext spiderContext = SpiderContextThreadLocalStore.get();
      String[] includeTypes = spiderContext.getIncludeTypes();
      String[] excludeTypes = spiderContext.getExcludeTypes();
      isNeed = (ArrayUtils.isEmpty(includeTypes)
              || new HashSet<>(Arrays.asList(includeTypes)).contains(taskType))
              && (ArrayUtils.isEmpty(excludeTypes)
              || !new HashSet<>(Arrays.asList(excludeTypes)).contains(taskType));
    }
    return isNeed;

  }

}
