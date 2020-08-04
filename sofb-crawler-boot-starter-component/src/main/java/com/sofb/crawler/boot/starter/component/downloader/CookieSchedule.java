package com.sofb.crawler.boot.starter.component.downloader;

import com.sofb.crwaler.framework.common.cookie.CookieQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * @author liuxuejun
 * @date 2019-12-24 11:25
 */
@Slf4j
public class CookieSchedule {

    @Resource(name = "qfangCookieQueue")
    CookieQueue qfangCookieQueue;

    @Resource(name = "zhongyuanCookieQueue")
    CookieQueue zhongyuanCookieQueue;

    @Scheduled(fixedRate = 1000000)
    public void qfangCookie() {
        qfangCookieQueue.pushCookie();
        log.info("qfang Cookie queue len is  " + qfangCookieQueue.getSize());
    }

    @Scheduled(fixedRate = 1000000)
    public void zhongYuanCookie() {
        zhongyuanCookieQueue.pushCookie();
        log.info("zhongyuan Cookie queue len is  " + zhongyuanCookieQueue.getSize());
    }
}
