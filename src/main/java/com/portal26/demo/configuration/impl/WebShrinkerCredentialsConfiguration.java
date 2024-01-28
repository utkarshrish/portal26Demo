package com.portal26.demo.configuration.impl;

import com.portal26.demo.configuration.CredentialsConfiguration;
import okhttp3.Credentials;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebShrinkerCredentialsConfiguration implements CredentialsConfiguration {

    @Value("${shrinker.accessKey:}")
    private String webShrinkerAccessKey = null;

    @Value("${shrinker.secretKey:}")
    private String webShrinkerSecretKey = null;

    @Bean(name = "shrinkerCredentialsProvider")
    public CredentialsProvider credentialsProvider() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(webShrinkerAccessKey, webShrinkerSecretKey));
        return credentialsProvider;
    }

    @Bean(name = "shrinkerCredentials")
    public String credential(){
        return Credentials.basic(webShrinkerAccessKey, webShrinkerSecretKey);
    }

}