package com.sofb.crawler.boot.starter.processor.model.col;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-22 15:40
 */
@Getter
@ToString
public class ColResult implements Serializable {

  private static final long serialVersionUID = 343253325336695170L;
  private ColArgs colArgs;

  /** cell 返回结果 */
  private List<Map<String, String>> result;

  private List<String> cleanResult = new ArrayList<>();

  public static ColResult getInstance(String fieldName, String pattern) {

    ColResult colResult = new ColResult();
    colResult.colArgs = ColArgs.getInstance(fieldName, pattern);
    colResult.result = new ArrayList<>();
    return colResult;
  }

  /** @return 获取匹配的第一个结果 */
  public Map<String, String> get() {
    return emptyResult() ? null : result.get(0);
  }

  /** @return 获取匹配所有结果 */
  public List<Map<String, String>> all() {
    return emptyResult() ? new ArrayList<>() : result;
  }

  public String getCleanResult() {
    return emptyResult() ? null : cleanResult.get(0);
  }

  public List<String> allCleanResult() {
    return emptyResult() ? new ArrayList<>() : cleanResult;
  }

  private boolean emptyResult() {
    return result == null || result.isEmpty();
  }

  public void setResult(String field, String value) {

    result.add(
        new HashMap<String, String>(8) {
          {
            put(field, value);
          }
        });
    cleanResult.add(value);
  }

  public void setResult(String field, List<String> values) {
    if (CollectionUtils.isNotEmpty(values)) {
      values.forEach(m -> setResult(field, m));
    } else {
      setResult(field, "");
    }
  }

  public void setResult(Map<String, String> fieldResultMap) {

    result.add(fieldResultMap);
  }

  public void setResult(List<Map<String, String>> fieldResultList) {

    result.addAll(fieldResultList);
  }

  public String getFieldName() {
    return colArgs.getFieldName();
  }

  public String getPattern() {
    return colArgs.getPattern();
  }
}
