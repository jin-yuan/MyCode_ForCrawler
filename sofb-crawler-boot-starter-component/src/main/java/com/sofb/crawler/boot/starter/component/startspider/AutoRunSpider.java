package com.sofb.crawler.boot.starter.component.startspider;

import com.sofb.crawler.boot.starter.component.startspider.start.StartSpider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * @author liuxuejun
 * @date 2020-01-17 14:40
 **/
public class AutoRunSpider implements ApplicationRunner {

    @Resource
    ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始启动爬虫");
        StartSpider startSpider = context.getBean(StartSpider.class);
        StartParams startParams = context.getBean(StartParams.class);
        startSpider.start(startParams);
    }
}