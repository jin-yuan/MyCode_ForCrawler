package com.sofb.crawler.boot.example;

import com.sofb.crawler.boot.starter.component.startspider.StartParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/** @author liuxuejun */
@SpringBootApplication
@EnableScheduling
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootDemoApplication.class, args);

        System.out.println(
                applicationContext.getBean(StartParams.class));
  }

}
