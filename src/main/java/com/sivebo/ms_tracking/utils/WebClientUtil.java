package com.sivebo.ms_tracking.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.sivebo.ms_tracking.exception.MicroserviceUnavailableException;
import com.sivebo.ms_tracking.exception.MicroserviceValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebClientUtil {

        public void validateMicroServiceById(Long id, String name_service, WebClient webClient) {
                try {
                        webClient.get()
                                .uri("/api/v1/" + name_service + "/{id}", id)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
                        log.info(">>> {} {} validado correctamente (WebClient)", name_service, id);
                } catch (WebClientResponseException.NotFound e) {
                        throw new MicroserviceValidationException(
                                name_service + " con id " + id + " no existe en el microservicio.");
                } catch (Exception e) {
                        throw new MicroserviceUnavailableException(
                                "No se pudo conectar con el microservicio: " + e.getMessage());
                }
        }

        public void validateMicroServiceByQuery(String name_service, String query, String value, WebClient webClient) {
                try {
                        webClient.get()
                                .uri("/api/v1/" + name_service + "/search?" + query + "=" + value)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
                        log.info(">>> {} {} validado correctamente (WebClient)", name_service, value);
                } catch (WebClientResponseException.NotFound e) {
                        throw new MicroserviceValidationException(
                                name_service + " con " + query + "=" + value + " no existe en el microservicio.");
                } catch (Exception e) {
                        throw new MicroserviceUnavailableException(
                                "No se pudo conectar con el microservicio: " + e.getMessage());
                }
        }
}
