package com.sofb.crawler.boot.example;

import com.sofb.crawler.boot.starter.processor.parse.CallBackFunctionManager;
import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author liuxuejun
 * @date 2019-12-23 14:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerConfigTest {

  @Resource private CallBackFunctionManager callBackFunctionManager;

  @Test
  public void filterTest() {}

  @Test
  public void PreListenerTest() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.setPlatformName("lianjia");
    //        platformPayLoad.addRequest("housedetail",
    // "https://sz.lianjia.com/ershoufang/105103304952.html?tdsourcetag=s_pctim_aiomsg", "new",
    // callBackFunctionManager.defaultCallBack());
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void AfterListenerTest() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.setPlatformName("leyoujia");
    //        platformPayLoad.addRequest("gardenDetail",
    // "https://shenzhen.leyoujia.com/xq/detail/54.html#top",
    // callBackFunctionManager.defaultCallBack());
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void RegionTest() {}
}
