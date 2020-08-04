package com.sofb.crawler.boot.starter.component.startspider;

import com.sofb.crawler.boot.starter.component.startspider.start.SimpleStartSpider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author liuxuejun
 * @date 2019-12-31 16:24
 **/
@Configuration
@Import({SimpleStartSpider.class, AutoRunSpider.class})
public class StartSpiderConfig {

    @Bean
    @ConfigurationProperties(prefix = "sofb.crawler.start")
    public StartParams startParams() {
        return new StartParams();
    }
}
