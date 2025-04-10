package org.nurgisa.pointrate.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nurgisa.pointrate.exception.NullResponseException;
import org.nurgisa.pointrate.model.ScoreType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GisApiClient {
    private static final Logger logger = LoggerFactory.getLogger(GisApiClient.class);

    private final WebClient webClient;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    @Autowired
    public GisApiClient(@Qualifier("gis.places") WebClient webClient,
                        @Value("${gis.api-key}") String apiKey, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    public int counter(double lat, double lon, ScoreType type) {

        Mono<String> result = switch (type) {
            case EDUCATION -> getEducation(lat, lon);
            case SPORTS -> getSports(lat, lon);
            case ENTERTAINMENT -> getEntertainment(lat, lon);
            case TRANSPORT -> getTransport(lat, lon);
            case ECOLOGY -> getEcology(lat, lon);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        if (result != null) {
            String response = result.block();

            try {
                JsonNode rootNode = objectMapper.readTree(response);

                return rootNode.path("result").path("total").asInt();
            }
            catch (JsonProcessingException e) {
                logger.error("Error while processing JSON in response", e);
                throw new RuntimeException(e);
            }
        }
        else {
            throw new NullResponseException("2GIS API: Result is null");
        }
    }

    private Mono<String> getEducation(double lat, double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", "школа")
                        .queryParam("point", lon + ", " + lat)
                        .queryParam("radius", 2000)
                        .queryParam("sort", "distance")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> getSports(double lat, double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", "спорт")
                        .queryParam("point", lon + ", " + lat)
                        .queryParam("radius", 1000)
                        .queryParam("sort", "distance")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> getEntertainment(double lat, double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", "развлечения")
                        .queryParam("point", lon + ", " + lat)
                        .queryParam("radius", 1500)
                        .queryParam("sort", "distance")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> getTransport(double lat, double lon) {
        // TODO(HOW TO MAKE IT AAA)
        return null;
    }

    private Mono<String> getEcology(double lat, double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", "парк")
                        .queryParam("point", lon + ", " + lat)
                        .queryParam("radius", 3000)
                        .queryParam("sort", "distance")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
