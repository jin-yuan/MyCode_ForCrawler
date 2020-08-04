package com.sofb.crawler.boot.starter.component.schedule;

import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.model.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuxuejun
 * @date 2019-12-24 10:09
 */
@Slf4j
public class DefaultCrawlerBootSchedule implements Scheduler {

    @Autowired
    RedisScheduleService redisScheduleService;

    @Override
    public void push(Request request) {
        redisScheduleService.pushRequest(request);
    }

    @Override
    public Request poll() {
        return redisScheduleService.pollRequest();
    }


}
