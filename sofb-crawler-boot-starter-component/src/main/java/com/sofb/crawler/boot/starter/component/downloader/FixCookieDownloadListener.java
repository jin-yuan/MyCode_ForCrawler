package com.sofb.crawler.boot.starter.component.downloader;

import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListener;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crwaler.framework.common.cookie.CookieQueue;
import com.sofb.crwaler.framework.common.decrpyt.LianjiaAppSign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author liuxuejun
 * @date 2019-12-24 11:44
 */
@Slf4j
public class FixCookieDownloadListener implements DownloadListener {
    @Resource(name = "qfangCookieQueue")
    CookieQueue qfangCookieQueue;

    @Resource(name = "zhongyuanCookieQueue")
    CookieQueue zhongyuanCookieQueue;

    @Override
    public void preDownload(Request request) {
        if (request.getUrl().contains("qfang")) {
            log.info("{}开始使用cookie,队列cookie有{}", request.getUrl(), qfangCookieQueue.getSize());
            Map<String, String> cookieMap = qfangCookieQueue.peekCookie();
            if (MapUtils.isNotEmpty(cookieMap)) {
                cookieMap.forEach(request::addCookie);
            }
        }

        if (request.getUrl().contains("centanet")) {
            log.info("{}开始使用cookie,队列cookie有{}", request.getUrl(), zhongyuanCookieQueue.getSize());
            Map<String, String> cookieMap = zhongyuanCookieQueue.peekCookie();
            if (MapUtils.isNotEmpty(cookieMap)) {
                cookieMap.forEach(request::addCookie);
            }
        }

        if (request.getUrl().contains("app.api.lianjia")) {
            request.addHeader("Authorization", LianjiaAppSign.genSign(request.getUrl()));
            request.addHeader("User-Agent", " HomeLink 9.7.0;iPhone8,1;iOS 13.1;");
        }
    }

    @Override
    public void afterDownload(Response response) {
        if (response.getUrl().contains("centanet") && response.getStatus() == 200 && Optional.ofNullable(response.getText()).orElse(StringUtils.EMPTY).contains("acw_sc__v2")) {
            zhongyuanCookieQueue.genCookie(response.getText());
            log.error(String.format("下载 %s 不成功进入重推", response.getUrl()));
            throw new CrawlerException(CrawlerErrorCodeEnum.ANTI_CRAWLER_ERROR);
        }
    }
}
