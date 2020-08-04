package com.sofb.crawler.boot.starter.component.pipeline;

import com.sofb.crawler.framework.core.Pipeline;
import com.sofb.crawler.framework.core.model.ResultItems;

import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2020-01-02 11:02
 **/
public class DefaultCrawlerBootPipeline implements Pipeline<List<Map<String, String>>> {
    @Override
    public void process(ResultItems<List<Map<String, String>>> resultItems) {

    }
}
