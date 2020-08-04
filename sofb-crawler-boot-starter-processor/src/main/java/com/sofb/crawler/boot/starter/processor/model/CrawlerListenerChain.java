package com.sofb.crawler.boot.starter.processor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-21 10:52
 */
@ToString
@Setter
@Getter
public class CrawlerListenerChain {

  /** 前置过滤 */
  private Map<String, String> pre;
  /** 后置过滤链 */
  private Map<String, String> after;

  void mergeChain(CrawlerListenerChain crawlerListenerChain) {
    if (crawlerListenerChain != null) {
        if (this.getPre() != null && crawlerListenerChain.getPre() != null) {
        this.getPre().putAll(crawlerListenerChain.getPre());
      } else if (crawlerListenerChain.pre != null) {
        this.setPre(crawlerListenerChain.pre);
      }

        if (this.getAfter() != null && crawlerListenerChain.getAfter() != null) {
        this.getAfter().putAll(crawlerListenerChain.getAfter());
      } else if (crawlerListenerChain.after != null) {
        this.setAfter(crawlerListenerChain.after);
      }
    }
  }
}
