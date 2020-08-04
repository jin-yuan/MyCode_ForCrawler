package com.sofb.crawler.boot.starter.component.startspider.start;

import com.sofb.crawler.boot.starter.component.startspider.StartParams;
import com.sofb.crawler.framework.core.Pipeline;
import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListener;
import com.sofb.crawler.framework.core.listener.global.GlobalListener;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author liuxuejun
 * @date 2020-01-07 15:12
 **/
@Slf4j
public class SimpleStartSpider extends AbstractStartSpider {


    @Override
    public void start(StartParams startParams) {

        if (startParams.isAutoRun()) {

            String projectName = startParams.getProjectName();

            Scheduler scheduler = getSchedule(startParams.isCluster());

            List<DownloadListener> downloadListeners = getBeanByNames(startParams.getDownloadListeners(), DownloadListener.class);

            List<GlobalListener> globalListeners = getBeanByNames(startParams.getGlobalListeners(), GlobalListener.class);

            List<Pipeline> pipelines = getBeanByNames(startParams.getPipelines(), Pipeline.class);


            log.info("项目 {} \n获取到 调度器 {} ，\n下载器 {} ，\n全局监听器 {} ,\n管道 {}", projectName, scheduler, downloadListeners, globalListeners, pipelines);

            startParams.getPlatformThreadNum().forEach((platform, threadNum) -> {
                log.info("开始初始化 {} 任务", platform);
                PlatformPayLoad platformPayLoad = new PlatformPayLoad();

                platformPayLoad.addDownLoadListener(downloadListeners);

                platformPayLoad.addGlobalListener(globalListeners);

                platformPayLoad.addPipeLine(pipelines);

                platformPayLoad.setSchedule(scheduler);

                platformPayLoad.setAsyncRun(true);

                platformPayLoad.setAbleKeepAlive(true);

                platformPayLoad.setUseProxyRate(Optional.ofNullable(startParams.getPlatformUseProxyRate()).orElse(new HashMap<>()).getOrDefault(platform, 0.0));

                platformPayLoad.setProjectName(projectName);

                platformPayLoad.setPlatformName(platform);

                platformPayLoad.setThreadNum(threadNum);

                platformPayLoad.setTaskQueueSuffix(getSuffix(startParams.getSuffix()));

                platformPayLoad.setConsumeTypes(getConsumeType(startParams.getConsumeType()));

                platformPayLoad.addCallBack(getCallBackFuncMap());

                platformPayLoad.setExtension(startParams.getExtension());

                Spider.runWithPlatformPayLoad(platformPayLoad);

            });


        }


    }


}
