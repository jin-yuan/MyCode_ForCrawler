package com.sofb.crawler.boot.starter.component.downloader;

import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListener;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.model.SpiderContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author sofb
 */

@Slf4j
public class FixProxyDownloaderListener implements DownloadListener {


    @Override
    public void preDownload(Request request) {
        SpiderContext spiderContext = SpiderContextThreadLocalStore.get();
        if (spiderContext.getUseProxyRate() > new Random().nextFloat()) {
            if (ProxySchedule.proxy == null) {
                ProxySchedule.proxy = ProxySchedule.addProxy();
            }
            log.info("允许使用代理 {}", ProxySchedule.proxy);
            request.setProxy(ProxySchedule.proxy);
        }

    }

    @Override
    public void afterDownload(Response response) {

    }
}
