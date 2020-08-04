package com.sofb.crawler.boot.starter.component.startspider.start;

import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import com.sofb.crawler.framework.core.schedule.StandAloneScheduler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractStartSpider implements StartSpider {

    @Autowired
    ApplicationContext applicationContext;


    /**
     * 获取调度
     *
     * @param isCluster 是否是集群
     * @return schedule
     */
    Scheduler getSchedule(boolean isCluster) {
        if (isCluster) {
            return applicationContext.getBean(Scheduler.class);
        } else {
            return new StandAloneScheduler();
        }

    }


    Map<String, CallBackFunction> getCallBackFuncMap() {
        return applicationContext.getBeansOfType(CallBackFunction.class);

    }

    /**
     * 获取监听器泛型方法
     *
     * @param listenerBeanNames 监听器配置名
     * @param t                 监听器类
     * @param <T>               监听器泛型类
     * @return 监听器列表
     */
    <T> List<T> getBeanByNames(String listenerBeanNames, Class<T> t) {

        if (StringUtils.isNotEmpty(listenerBeanNames)) {

            return Arrays.stream(listenerBeanNames.split(",")).map(beanName -> applicationContext.getBean(beanName, t)).collect(Collectors.toList());
        } else {
            return new ArrayList<T>(applicationContext.getBeansOfType(t).values());
        }

    }


    List<String> getSuffix(String suffix) {
        if (StringUtils.isEmpty(suffix)) {
            return Arrays.asList("online", "normal", "old", "test");
        }

        return Arrays.asList(suffix.split(","));
    }


    List<String> getConsumeType(String consumeType) {
        if (StringUtils.isEmpty(consumeType)) {
            return Arrays.asList("new", "update", "all", "fast");
        }

        return Arrays.asList(consumeType.split(","));

    }
}
