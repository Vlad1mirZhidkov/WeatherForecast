package com.example.demo.service;

import com.example.demo.DTO.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {
    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline").build();
    }

    public Mono<WeatherData> getWeatherData(String location) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{location}")
                        .queryParam("unitGroup", "metric")
                        .queryParam("key", "JFS34ZDQWCGXAR8ZEWWV87VSM")
                        .queryParam("contentType", "json")
                        .build(location))
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonString -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(jsonString, WeatherData.class);
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            );
    }
}