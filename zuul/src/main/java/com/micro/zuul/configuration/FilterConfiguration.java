package com.micro.zuul.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
}
