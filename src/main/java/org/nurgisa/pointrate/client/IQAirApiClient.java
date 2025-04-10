package org.nurgisa.pointrate.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nurgisa.pointrate.exception.NullResponseException;
import org.nurgisa.pointrate.exception.RequestFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class IQAirApiClient {
    private static final Logger logger = LoggerFactory.getLogger(IQAirApiClient.class);

    private final WebClient webClient;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public IQAirApiClient(@Qualifier("iqair.api") WebClient webClient,
                          @Value("${iqair.api-key}") String apiKey,
                          ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    public double getAirQualityScore() {
        Mono<String> result = requestForAirQuality();

        if (result == null) {
            throw new NullResponseException("IQ AIR API: Result is null");
        }

        String response = result.block();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            String status = rootNode.get("status").asText();

            if (status.equals("success")) {
                int aqi = rootNode
                        .path("data")
                        .path("current")
                        .path("pollution")
                        .path("aqius")
                        .asInt();

                return getAqiScore(aqi);
            }
            else {
                throw new RequestFailureException("Request failed: " + status);
            }
        }
        catch (JsonProcessingException e) {
            logger.error("Error while processing JSON in response", e);
            throw new RuntimeException(e);
        }
    }

    public double getAqiScore(int aqi) {
        int cappedAqi = Math.min(Math.max(aqi, 0), 300);

        double score = 10.0 - 9.0 * (cappedAqi / 300.0);
        return Math.round(score * 100.0) / 100.0;
    }

    public Mono<String> requestForAirQuality() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("city", "Almaty")
                        .queryParam("state", "Almaty Oblysy")
                        .queryParam("country", "Kazakhstan")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
