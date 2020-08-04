package com.sofb.crawler.boot.example;

import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-11-23 14:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringApplicationTest {

  @Autowired Map<String, CallBackFunction> callBackFunctionMap;

  @Test
  public void secondHandProcessDetailPage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.addCallBack(callBackFunctionMap);
    platformPayLoad.setPlatformName("lianjia");
    platformPayLoad.addRequest(
            "housedetail", "https://sz.lianjia.com/ershoufang/105103427325.html");
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void secondHandProcessPage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.addCallBack(callBackFunctionMap);
    platformPayLoad.setPlatformName("lianjia");
    platformPayLoad.setThreadNum(3);
    platformPayLoad.addRequest("pages", "https://sz.lianjia.com/ershoufang/pg3/");
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void secondHandProcessHomePage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.setPlatformName("lianjia");
    platformPayLoad.setThreadNum(3);
      platformPayLoad.addCallBack(callBackFunctionMap);
      platformPayLoad.addRequest("homepage", "https://sz.lianjia.com/ershoufang/");
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }
}
