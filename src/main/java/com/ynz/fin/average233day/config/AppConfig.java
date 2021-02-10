package com.ynz.fin.average233day.config;


import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;


@Configuration
@PropertySource("classpath:finnhubresource.properties")
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "10Days")
    public Integer tenDaySpan() {
        return 10;
    }

    @Bean(name = "20Days")
    public Integer twentyDaySpan() {
        return 20;
    }

    @Bean("simpleRegression")
    public SimpleRegression getSimpleRegression() {
        return new SimpleRegression();
    }

}
