package com.sofb.crawler.boot.example;

import com.sofb.crawler.boot.starter.component.schedule.DefaultCrawlerBootSchedule;
import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.listener.global.GlobalListener;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-12-24 16:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisScheduleTest {

  @Autowired Map<String, CallBackFunction> callBackFunctionMap;
  @Autowired DefaultCrawlerBootSchedule redisSchedule;


  @Autowired(required = false)
  private List<GlobalListener> globalListeners;

  @Test
  public void secondHandProcessDetailPage() {
    System.out.println(callBackFunctionMap);
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.addCallBack(callBackFunctionMap);
    platformPayLoad.setPlatformName("lianjia");
    platformPayLoad.setProjectName("ershoufang");
    platformPayLoad.setSchedule(redisSchedule);
    platformPayLoad.addRequest(
        "housedetail",
        "https://sz.lianjia.com/ershoufang/105102624152.html",
        "all",
        "sofb_crawler:ershoufang:lianjia:all:normal");
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void secondHandProcessErrorDetailPage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.setPlatformName("lianjiassssssssssss");
    platformPayLoad.setProjectName("ershoufangssss");
    platformPayLoad.addCallBack(callBackFunctionMap);
    platformPayLoad.setSchedule(redisSchedule);
    platformPayLoad.addGlobalListener(globalListeners);
    platformPayLoad.addRequest(
        "housedetail",
            "https://sz.lianjia.com/ershoufang/",
        "all",
        "sofb_crawler:ershoufang:lianjia:all:normal");
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void secondHandProcessPage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.addCallBack(callBackFunctionMap);
    platformPayLoad.setPlatformName("lianjia");
    platformPayLoad.setProjectName("ershoufang");
    platformPayLoad.setSchedule(redisSchedule);
    platformPayLoad.setTaskQueueSuffix(Arrays.asList("test"));
    platformPayLoad.setAbleKeepAlive(true);
    platformPayLoad.addRequest(
        "pages",
        "https://sz.lianjia.com/ershoufang/pg3/",
            "fast",
            "sofb_crawler:ershoufang:lianjia:fast:test");
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

}
