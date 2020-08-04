package com.sofb.crawler.boot.example.parsebean;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ZhaoJianLin
 * @version 1.0
 * @date 2019/12/19 14:51
 */
public class MyCollectUtil extends CollectionUtils {

  public static boolean isNotEmpty(@Nullable Collection<?> collection) {
    return !isEmpty(collection);
  }

  public static <T> T getFirstChild(List<T> items) {
    if (isEmpty(items)) return null;
    return items.get(0);
  }

  public static <T> List<T> objectToList(T t) {
    List<T> list = new ArrayList<>();
    if (t == null) {
      return list;
    }
    list.add(t);
    return list;
  }
}
