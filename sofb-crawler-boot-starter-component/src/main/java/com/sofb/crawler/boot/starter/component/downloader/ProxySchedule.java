package com.sofb.crawler.boot.starter.component.downloader;

import com.alibaba.fastjson.JSON;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import com.sofb.crwaler.framework.common.net.httpclient.entity.Proxy;
import com.sofb.crwaler.framework.common.net.httpclient.executor.GenericDownloadExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class ProxySchedule {


    static Proxy proxy = null;

    public static Proxy addProxy() {
        HttpRequestEntity requestEntity = new HttpRequestEntity();
        Proxy proxy = new Proxy();
        requestEntity.setUrl(
                "http://api.wandoudl.com/api/ip?app_key=6f591c66226457cb6b5523bc7595179f&pack=0&xy=1&type=2&mr=1&tdsourcetag=s_pctim_aiomsg&port=4&num=");
        requestEntity.setMethod("GET");
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            String host = JSON.parseObject(responseEntity.getText()).getJSONArray("data").getJSONObject(0).get("ip").toString();
            String port = JSON.parseObject(responseEntity.getText()).getJSONArray("data").getJSONObject(0).get("port").toString();
            String expireTime = JSON.parseObject(responseEntity.getText()).getJSONArray("data").getJSONObject(0).get("expire_time").toString();

            proxy.setHost(host);
            proxy.setPort(Integer.parseInt(port));
            proxy.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
            proxy.setUsername("632333950@qq.com");
            proxy.setPassword("Aa123123123");
            return proxy;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Scheduled(fixedRate = 60000)
    public void refreshProxy() {
        if (proxy == null || proxy.isInvalid()) {
            proxy = addProxy();
            log.info("重新获取新的代理 {}", proxy);
        }
        log.info("代理没有过期，无需重新获取代理");
    }

}
