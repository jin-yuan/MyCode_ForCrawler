package com.sofb.crawler.boot.example.test;

import com.sofb.crawler.boot.starter.component.schedule.DefaultCrawlerBootSchedule;
import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-12-30 14:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerTaskTest {
    @Autowired
    Map<String, CallBackFunction> callBackFunctionMap;

    @Autowired
    DefaultCrawlerBootSchedule redisSchedule;

    @Test
    public void secondHandProcessPage() {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.addCallBack(callBackFunctionMap);
        platformPayLoad.setPlatformName("zhongyuan");
        platformPayLoad.setProjectName("ershoufang");
        platformPayLoad.setAbleKeepAlive(true);
        platformPayLoad.setTaskQueueSuffix(Arrays.asList("test"));
        platformPayLoad.addRequest(
                "pagess",
                "https://sz.centanet.com/ershoufang/g15/",
                "test",
                "sofb_crawler:ershoufang:lianjia:all:test");
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }


    @Test
    public void secondHandProcessPageWithRedis() {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.addCallBack(callBackFunctionMap);
        platformPayLoad.setPlatformName("lianjia");
        platformPayLoad.setProjectName("ershoufang");
        platformPayLoad.setSchedule(redisSchedule);
        platformPayLoad.setAbleKeepAlive(true);
        platformPayLoad.setTaskQueueSuffix(Arrays.asList("test"));
        platformPayLoad.addRequest(
                "pages",
                "https://sz.lianjia.com/ershoufang/pg3/",
                "all",
                "sofb_crawler:ershoufang:lianjia:all:test");
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }
}
