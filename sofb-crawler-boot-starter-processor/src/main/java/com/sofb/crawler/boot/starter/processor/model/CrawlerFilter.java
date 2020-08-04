package com.sofb.crawler.boot.starter.processor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liuxuejun
 * @date 2019-12-23 14:34
 */
@ToString
@Setter
@Getter
public class CrawlerFilter {

  /** 包含任务列类型 */
  private String includeTypes;

  /** 剔除任务类型 */
  private String excludeTypes;

  /** 包含表类型 */
  private String includeTables;

  /** 剔除任务列表 */
  private String excludeTables;

}
