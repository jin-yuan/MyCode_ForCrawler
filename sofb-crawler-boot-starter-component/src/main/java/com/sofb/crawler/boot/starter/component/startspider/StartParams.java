package com.sofb.crawler.boot.starter.component.startspider;

import lombok.Data;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-12-31 16:28
 **/
@Data
public class StartParams {

//    private List<PlatformPayLoad> spiderConfig;
    /**
     * 是否自动开启
     */
    private boolean autoRun = false;

    /**
     * 是否使用分布式
     */
    private boolean isCluster = true;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 渠道名和线程数
     */
    private Map<String, Integer> platformThreadNum;

    /**
     * 渠道使用代理概率
     */
    private Map<String, Double> platformUseProxyRate;

    private String downloadListeners;

    /**
     * 自定义pipeline
     */
    private String pipelines;

    /**
     * 自定义扩展
     */
    private Map<String, String> extension;


    /**
     * 全局监听器
     */
    private String globalListeners;


    private String suffix;


    private String consumeType;


}
