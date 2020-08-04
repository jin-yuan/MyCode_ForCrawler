package com.sofb.crawler.boot.starter.processor.parse.col;

import com.sofb.crwaler.framework.common.slector.SmartXpathSelector;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author liuxuejun
 * @date 2019-11-22 15:32
 */
@Slf4j
public class ParseColManager {

  public ParseColFunction xpath() {

    return (response, colResult) -> {
        colResult.setResult(
                colResult.getFieldName(), SmartXpathSelector.xpath(response.getText(), colResult.getPattern()));
    };
  }

  public ParseColFunction constant() {
    return (response, colResult) -> {
      colResult.setResult(colResult.getFieldName(), colResult.getPattern());
    };
  }

  public ParseColFunction requestExtra() {
    return (response, colResult) -> {
      colResult.setResult(
          colResult.getFieldName(),
          Optional.ofNullable(response.getExtra(colResult.getPattern())).orElse("").toString());
    };
  }
}
