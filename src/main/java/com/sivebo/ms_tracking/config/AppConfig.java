package com.sivebo.ms_tracking.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

        @Bean
        @LoadBalanced
        public WebClient.Builder webClientBuilder() {
                return WebClient.builder();
        }

        @Bean
        public WebClient admisionWebClient(WebClient.Builder webClientBuilder) {
                return webClientBuilder
                        .baseUrl("http://ms-admision")
                        .build();
        }

}
