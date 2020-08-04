package com.sofb.crawler.boot.starter.processor.parse.filter;

import org.seimicrawler.xpath.JXDocument;

/**
 * @author liuxuejun
 * @date 2019-12-17 14:41
 */
public class ParseFilterManager {

  public ParseFilterFunction addExtraWithXpath() {
    return (response, pattern) -> {
      String[] patterns = pattern.split("->", 2);
      response
          .getExtras()
          .put(patterns[0], JXDocument.create(response.getText()).selOne(patterns[1]));
      return false;
    };
  }

  public ParseFilterFunction isSaleOut() {
    return (response, pattern) -> JXDocument.create(response.getText()).selOne(pattern) != null;
  }
}
