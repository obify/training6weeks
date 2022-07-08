package com.mycompany.orderservice.client.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new BookFeignDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }

}
