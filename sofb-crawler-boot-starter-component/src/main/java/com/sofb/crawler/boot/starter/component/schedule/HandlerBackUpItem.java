package com.sofb.crawler.boot.starter.component.schedule;

import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.listener.global.GlobalListener;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author liuxuejun
 * @date 2020-01-10 10:28
 **/
@Slf4j
public class HandlerBackUpItem implements GlobalListener {

    @Resource
    RedisScheduleService redisScheduleService;

    @Override
    public void beforeInit() {

    }

    @Override
    public void afterInit(Request request) {
        redisScheduleService.backUpItem(request);
    }

    @Override
    public void onSuccess(Request request, Response response) {
        redisScheduleService.delBackUpItem(request);
    }

    @Override
    public void onError(Request request, Response response, CrawlerException e) {

    }

    @Override
    public void unCatch(Request request, Response response, CrawlerException e) {
        log.info("请求 {} 相应文本 {} 发生异常 {}", request, Optional.ofNullable(response.getText()).orElse("").substring(0, 100), e);
        redisScheduleService.pushErrorQueue(request);
    }
}
