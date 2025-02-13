package com.music.metadata.resource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${song.service.url}")
    private String songServiceUrl;

    @Bean
    public WebClient songServiceClient() {
        return WebClient.builder()
                .baseUrl(songServiceUrl)
                .build();
    }
} 