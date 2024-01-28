package com.portal26.demo.service;

import com.amazonaws.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal26.demo.response.ShrinkerResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ShrinkerService {

    @Value("${shrinker.endpoint}")
    private String endpoint;

    @Autowired
    String shrinkerCredentials;

    @Autowired
    OkHttpClient shrinkerRestClient;

    public String getCategory(String domain) {
        ObjectMapper mapper = new ObjectMapper();
        String base64Domain = Base64.encodeAsString(domain.getBytes(StandardCharsets.UTF_8));
        Request httpRequest = new Request.Builder()
                .url(endpoint + base64Domain)
                .header("content-type", "application/json")
                .header(HttpHeaders.AUTHORIZATION, shrinkerCredentials)
                .build();

        Call call = shrinkerRestClient.newCall(httpRequest);

        try {
            ShrinkerResponse response = mapper.readValue(call.execute().body().string(),
                    ShrinkerResponse.class);
            return response.getData().get(0).getCategories().get(0).getLabel();
        } catch (Exception e) {
            log.error("Exception occured while calling Web Shrinker", e);
            return "";
        }
    }
}
