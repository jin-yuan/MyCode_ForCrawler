package com.sofb.crawler.boot.example.parsebean;

import com.sofb.crawler.boot.starter.processor.parse.col.ParseColFunction;
import com.sofb.crawler.boot.starter.processor.parse.filter.ParseFilterFunction;
import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.ResultItems;
import com.sofb.crawler.framework.core.util.GenRequestFromUrl;
import com.sofb.crwaler.framework.common.util.SofbStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-12-23 15:32
 */
@Configuration
public class LeyoujiaParseBean {

  @Bean
  public ParseFilterFunction hasGardenStrategy() {

    return (response, pattern) -> {
      if (StringUtils.isNotEmpty(
          SofbStringUtils.getMatchesGroup1Text(pattern, response.getText(), null))) {
        String baseUrl = "https://shenzhen.leyoujia.com/xq/strategy/detail/%s.html";
        Map<String, Object> params = new HashMap<>();
        List<Map<String, String>> a = response.getResultItems().getItems().get("garden");
        params.putAll(a.get(0));
        Request request =
            GenRequestFromUrl.addRequest(
                String.format(baseUrl, StringUtils.getDigits(response.getUrl())),
                "gardenStrategy",
                params,
                response);
        request.setItems(response.getResultItems().getItems());
        response.getRequestList().add(request);
        response.setResultItems(new ResultItems<>());
      }

      return false;
    };
  }

  @Bean
  public ParseColFunction simpleImg() {

    return (response, colResult) -> {
      List<Map<String, String>> result = new ArrayList<>();

      String gardenId = MyUtils.trimWhitespace(MyNumUtil.getNumbers(response.getUrl()));

      List<Object> gardenUrls = JXDocument.create(response.getText()).sel(colResult.getPattern());

      if (!MyCollectUtil.isEmpty(gardenUrls) || !MyUtils.isEmpty(gardenId)) {

        for (Object url : gardenUrls) {

          result.add(
              new HashMap<String, String>(5) {
                {
                  put("CrawlImageUrl", url.toString());
                  put("GardenId", gardenId);
                  put("CreateDate", gardenId);
                  put(
                      "Id",
                      MyUtils.generateId(
                          SpiderContextThreadLocalStore.get().getPlatformName()
                              + SpiderContextThreadLocalStore.get().getCity()
                              + url.toString()));
                }
              });
        }
      }
      colResult.setResult(result);
    };
  }
}
