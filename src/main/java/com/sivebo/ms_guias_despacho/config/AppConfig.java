package com.sivebo.ms_guias_despacho.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
        
        @Value("${ms.admision.url}")
        private String admisionBaseUrl;

        @Bean
        public WebClient.Builder webClientBuilder() {
                return WebClient.builder();
        }

        @Bean
        public WebClient admisionWebClient(WebClient.Builder webClientBuilder) {
                return webClientBuilder
                        .baseUrl(admisionBaseUrl)
                        .build();
        }

}
