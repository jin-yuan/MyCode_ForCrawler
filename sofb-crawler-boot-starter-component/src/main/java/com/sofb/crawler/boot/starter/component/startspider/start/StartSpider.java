package com.sofb.crawler.boot.starter.component.startspider.start;

import com.sofb.crawler.boot.starter.component.startspider.StartParams;

/**
 * @author liuxuejun
 * @date 2020-01-08 10:06
 **/
public interface StartSpider {

    /**
     * 启动核心类
     *
     * @param startParams 启动参数
     */
    void start(StartParams startParams);
}
