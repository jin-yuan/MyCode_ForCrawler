package com.sofb.crawler.boot.example.parsebean;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.sofb.crawler.boot.starter.processor.parse.col.ParseColFunction;
import com.sofb.crawler.boot.starter.processor.parse.filter.ParseFilterFunction;
import com.sofb.crwaler.framework.common.util.SofbStringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-10-30 15:32
 */
@Configuration
public class LianjiaParseBean {

  private static String cleanSpace(String rawString) {

    return CharMatcher.whitespace().trimFrom(rawString);
  }

  @Bean
  public ParseColFunction houseImageBean() {
    return (response, colResult) -> {

      List<JXNode> jxNodes =
              JXDocument.create(response.getText()).selN("//ul[@class='smallpic']/li");
      List<Map<String, String>> resultMap = new ArrayList<>();
      for (int i = 0; i < jxNodes.size(); i++) {

        String url = jxNodes.get(i).sel("//li/@data-src").get(0).toString();
        String tag = jxNodes.get(i).sel("//li/@data-desc").get(0).toString();
        resultMap.add(
            new HashMap<String, String>(5) {
              {
                put("url", url);
                put("tag", tag);
              }
            });
      }
      colResult.setResult(resultMap);
    };
  }

  @Bean
  public ParseFilterFunction isInvalid() {
    return (response, pattern) -> {
      String[] patterns = pattern.split("->", 2);
      response
          .getExtras()
          .put(patterns[0], JXDocument.create(response.getText()).selOne(patterns[1]));
      return false;
    };
  }

  @Bean
  public ParseColFunction lianjiaPages() {
    return (response, colResult) -> {
      String baseUrl =
              JXDocument.create(response.getText())
                      .selOne("//div[@class='page-box house-lst-page-box']/@page-url")
                      .toString()
                      .replaceAll("\\{page\\}", "%d");
      int totalPage =
              Integer.parseInt(
                      JSONObject.parseObject(
                              JXDocument.create(response.getText())
                                      .selOne("//div[@class='page-box house-lst-page-box']/@page-data")
                                      .toString())
                              .getString("totalPage"));

      colResult.setResult(
              colResult.getFieldName(), SofbStringUtils.genPageUrls(baseUrl, totalPage));
    };
  }


}
