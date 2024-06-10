package com.example.demo.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OAuthApplicationService {
    @Value("${oauth.client.id:}")
    String clientId;

    @Value("${oauth.client.secret:}")
    String clientSecret;

    public String auth(String code) {
        RestTemplate restTemplate = new RestTemplate();
        OAuthRequest body = new OAuthRequest(
                this.clientId,
                this.clientSecret,
                code
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OAuthRequest> httpEntity = new HttpEntity<>(body, httpHeaders);
        OAuthResponse response = restTemplate.postForEntity("https://github.com/login/oauth/access_token", httpEntity, OAuthResponse.class).getBody();
        return response.getAccess_token();
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class OAuthRequest {
        String client_id;
        String client_secret;
        String code;
    }

    @ToString
    @Getter
    @NoArgsConstructor
    static class OAuthResponse {
        String access_token;
    }
}
