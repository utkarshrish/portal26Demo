package com.portal26.demo.configuration.impl;

import com.portal26.demo.configuration.CredentialsConfiguration;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSCredentialsConfiguration implements CredentialsConfiguration {

    @Value("${aws.es.accessKey:}")
    private String esAccessKey = null;

    @Value("${aws.es.secretKey:}")
    private String esSecretKey = null;

    @Bean(name = "awsCredentialsProvider")
    public CredentialsProvider credentialsProvider() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esAccessKey, esSecretKey));
        return credentialsProvider;
    }

}