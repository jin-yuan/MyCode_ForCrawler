package com.sofb.crawler.boot.example;

import com.sofb.crawler.boot.starter.processor.parse.CallBackFunctionManager;
import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListener;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuxuejun
 * @date 2019-12-24 11:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadListenerTest {

  @Resource List<DownloadListener> downloadListeners;
  @Resource private CallBackFunctionManager callBackFunctionManager;

  @Test
  public void secondHandProcessDetailPage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.setPlatformName("qfang");
    platformPayLoad.setAbleKeepAlive(true);
    platformPayLoad.addDownLoadListener(downloadListeners);
    //        platformPayLoad.addRequest("houseDetail",
    // "https://shenzhen.qfang.com/sale/100638237?insource=sale_list&top=1",
    // callBackFunctionManager.defaultCallBack());
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }

  @Test
  public void secondHandProcessPage() {
    PlatformPayLoad platformPayLoad = new PlatformPayLoad();
    platformPayLoad.setPlatformName("qfang");
    platformPayLoad.setAbleKeepAlive(true);
    platformPayLoad.addDownLoadListener(downloadListeners);
    //        platformPayLoad.addRequest("page", "https://shenzhen.qfang.com/sale",
    // callBackFunctionManager.defaultCallBack());
    Spider.runWithPlatformPayLoad(platformPayLoad);
  }
}
