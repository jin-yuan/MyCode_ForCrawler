package com.sofb.crawler.boot.starter.component;

import com.sofb.crawler.boot.starter.component.downloader.CookieSchedule;
import com.sofb.crawler.boot.starter.component.downloader.FixCookieDownloadListener;
import com.sofb.crawler.boot.starter.component.downloader.FixProxyDownloaderListener;
import com.sofb.crawler.boot.starter.component.downloader.ProxySchedule;
import com.sofb.crawler.boot.starter.component.gloablelistener.SendMailGlobalListener;
import com.sofb.crawler.boot.starter.component.schedule.DefaultCrawlerBootSchedule;
import com.sofb.crawler.boot.starter.component.schedule.HandlerBackUpItem;
import com.sofb.crawler.boot.starter.component.schedule.RedisScheduleService;
import com.sofb.crwaler.framework.common.cookie.AcwCookie;
import com.sofb.crwaler.framework.common.cookie.CookieQueue;
import com.sofb.crwaler.framework.common.cookie.CookieQueueConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author liuxuejun
 * @date 2019-12-24 10:06
 */
@Configuration
@Import({
        CookieSchedule.class,
        FixCookieDownloadListener.class,
        DefaultCrawlerBootSchedule.class,
        RedisScheduleService.class,
        HandlerBackUpItem.class, FixProxyDownloaderListener.class, ProxySchedule.class
})
public class EnableAutoCrawlerComponent {

    @Bean(name = "qfangCookieQueue")
    public CookieQueue qfangCookieQueue() {
        CookieQueueConfig cookieQueueConfig =
                CookieQueueConfig.builder()
                        .testUrl("https://shenzhen.qfang.com/sale")
                        .iCookie(new AcwCookie())
                        .keyCookieName("acw_sc__v2")
                        .maxCount(10)
                        .build();

        return new CookieQueue(cookieQueueConfig);
    }

    @Bean(name = "zhongyuanCookieQueue")
    public CookieQueue zhongyuanCookieQueue() {
        CookieQueueConfig cookieQueueConfig =
                CookieQueueConfig.builder()
                        .testUrl("https://sz.centanet.com/ershoufang/u7g114/")
                        .iCookie(new AcwCookie())
                        .keyCookieName("acw_sc__v2")
                        .maxCount(5)
                        .build();

        return new CookieQueue(cookieQueueConfig);
    }

    @Bean
    @ConditionalOnProperty(name = {"spring.mail.username"})
    @ConditionalOnBean(JavaMailSender.class)
    public SendMailGlobalListener sendMailGlobalListener() {
        return new SendMailGlobalListener();
    }
}
