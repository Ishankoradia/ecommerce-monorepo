package com.ecommerce.productservice.configurations;

import com.ecommerce.productservice.middlewares.ValidateTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MiddlewareConfig {


    @Bean
    public FilterRegistrationBean<ValidateTokenFilter> validateTokenFilter(RestTemplate restTemplate) {
        FilterRegistrationBean<ValidateTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ValidateTokenFilter(restTemplate));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // Set precedence if multiple filters
        return registrationBean;
    }
}
