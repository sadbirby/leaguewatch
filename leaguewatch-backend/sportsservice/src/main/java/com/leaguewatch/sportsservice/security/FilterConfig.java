package com.leaguewatch.sportsservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    Logger logger = LoggerFactory.getLogger(FilterConfig.class);

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new TokenFilter());
        filter.addUrlPatterns("/api/v1/sports/*");
        logger.info("Securing all endpoints with prefix " + filter.getUrlPatterns());
        return filter;
    }
}
