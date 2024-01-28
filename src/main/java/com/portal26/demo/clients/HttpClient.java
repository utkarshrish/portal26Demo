package com.portal26.demo.clients;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClient {

    @Bean
    public Map<String, String> categoryDomainMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    public OkHttpClient shrinkerRestClient(){
        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(10,5,TimeUnit.SECONDS))
                .build();
    }
}
