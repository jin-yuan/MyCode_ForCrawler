package com.sofb.crawler.boot.starter.processor.model.col;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author liuxuejun
 * @date 2019-11-23 15:28
 */
@Getter
class ColArgs implements Serializable {

  private static final long serialVersionUID = -7209433695514594009L;
  /** 字段名字 */
  private String fieldName;
  /** 解析参数 */
  private String pattern;

  static ColArgs getInstance(String fieldName, String pattern) {
    ColArgs colArgs = new ColArgs();
    colArgs.fieldName = fieldName;
    colArgs.pattern = pattern;

    return colArgs;
  }
}
