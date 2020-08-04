package com.sofb.crawler.boot.example.filter;

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
 * @date 2019-12-30 15:39
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerFilterTest {

    @Autowired
    Map<String, CallBackFunction> callBackFunctionMap;

    @Autowired
    DefaultCrawlerBootSchedule redisSchedule;


    @Test
    public void secondHandProcessDetailPageFilterHouseByContext() {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.addCallBack(callBackFunctionMap);
        platformPayLoad.setPlatformName("lianjia");
        platformPayLoad.setExcludeTables(new String[]{"house"});
        platformPayLoad.addRequest(
                "housedetail", "https://sz.lianjia.com/ershoufang/105103410731.html");
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }

    @Test
    public void secondHandProcessDetailPageFilterHouseimageByConfig() {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.addCallBack(callBackFunctionMap);
        platformPayLoad.setPlatformName("lianjia");
        platformPayLoad.addRequest(
                "housedetail",
                "https://sz.lianjia.com/ershoufang/105103410731.html"
//                "test",
//                "sofb_crawler:ershoufang:lianjia:all:normal"
        );
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }

    @Test
    public void secondHandProcessPage() {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.addCallBack(callBackFunctionMap);
        platformPayLoad.setPlatformName("lianjia");
        platformPayLoad.setProjectName("ershoufang");
        platformPayLoad.setAbleKeepAlive(true);
        platformPayLoad.setTaskQueueSuffix(Arrays.asList("test"));
        platformPayLoad.addRequest(
                "pages",
                "https://sz.lianjia.com/ershoufang/pg3/",
                "normal",
                "sofb_crawler:ershoufang:lianjia:all:test");
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }

}
