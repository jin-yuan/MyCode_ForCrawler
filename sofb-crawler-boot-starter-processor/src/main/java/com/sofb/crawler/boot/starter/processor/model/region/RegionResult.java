package com.sofb.crawler.boot.starter.processor.model.region;

import com.sofb.crawler.boot.starter.processor.model.table.TableResult;
import com.sofb.crawler.framework.core.model.Request;

import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-23 11:04
 */
public class RegionResult {

  private TableResult tableResult;

  private RegionArgs regionArgs;

    public static RegionResult getInstance(String platform, String requestType, String consumeType) {

    RegionResult regionResult = new RegionResult();
        regionResult.regionArgs = RegionArgs.getInstance(platform, requestType, consumeType);

    regionResult.tableResult = new TableResult();

    return regionResult;
  }

  public void merge(TableResult newResult) {
    if (!newResult.getItems().isEmpty()) {
      tableResult.getItems().putAll(newResult.getItems());
    }
    if (!newResult.getNewRequests().isEmpty()) {
      tableResult.getNewRequests().addAll(newResult.getNewRequests());
    }
  }

  public List<Request> getAllRequests() {
    return tableResult.getNewRequests();
  }

  public Map<String, List<Map<String, String>>> getAllItems() {
    return tableResult.getItems();
  }

  public String getPlatform() {
    return regionArgs.getPlatform();
  }

  public String getRequestType() {
    return regionArgs.getRequestType();
  }

    public String getConsumeType() {
        return regionArgs.getConsumeType();
    }
}
