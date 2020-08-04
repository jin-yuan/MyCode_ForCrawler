package com.sofb.crawler.boot.example.parsebean;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasLength;

/**
 * @author ZhaoJianLin
 * @version 1.0
 * @date 2019/11/14 14:26
 */
public class MyUtils {
  public static String trimWhitespace(String str) {
    if (!hasLength(str)) {
      return str;
    }
    StringBuilder buf = new StringBuilder(str);
    while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
      buf.deleteCharAt(0);
    }
    while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
      buf.deleteCharAt(buf.length() - 1);
    }
    return buf.toString();
  }

  public static String getNumbers(String content) {
    if (isEmpty(content)) {
      return "0";
    }
    Pattern pattern = Pattern.compile("[+-]?\\d+(\\.\\d+)?");
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
      return matcher.group(0);
    }
    return "0";
  }

  public static boolean isEmpty(String str) {
    return null == str || str.trim().length() < 1;
  }

  public static String getMatchesGroup1Text(String regex, String value) {
    if (isEmpty(regex) || isEmpty(value)) {
      return "";
    }
    Pattern r = Pattern.compile(regex);
    Matcher matcher = r.matcher(value);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return "";
  }

  public static String generateId(String... salt) {
    return generateId(StringUtils.join(salt));
  }

  private static String generateId(String id) {
    if (StringUtils.isEmpty(id)) {
      return null;
    }
    return DigestUtils.md5Hex(id);
  }
}
