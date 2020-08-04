package com.sofb.crawler.boot.example.parsebean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhaoJianLin
 * @version 1.0
 * @date 2019/11/28 9:46
 */
public class MyNumUtil {

  /** 正数是正数，负数是负数 */
  public static int toInt(Object obj) {
    try {
      if (null == obj) {
        return 0;
      }
      return Integer.valueOf(obj.toString());
    } catch (Exception e) {
      return 0;
    }
  }

  public static String getNumbers(String content) {
    if (MyUtils.isEmpty(content)) {
      return "0";
    }
    Pattern pattern = Pattern.compile("[+-]?\\d+(\\.\\d+)?");
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
      return matcher.group(0);
    }
    return "0";
  }
}
