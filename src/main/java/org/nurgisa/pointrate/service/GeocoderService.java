package org.nurgisa.pointrate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nurgisa.pointrate.dto.PointDTO;
import org.nurgisa.pointrate.exception.AddressNotFoundException;
import org.nurgisa.pointrate.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class GeocoderService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    @Autowired
    public GeocoderService(RestTemplateBuilder restTemplateBuilder,
                           @Value("${gis.api-key}") String apiKey,
                           @Value("${gis.geocoder.base-url}") String baseUrl,
                           ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
    }

    public String getAddress(double latitude, double longitude) {
        String url = String.format(Locale.US, "%s?lat=%f&lon=%f&fields=items.point&key=%s&locale=ru_KZ",
                baseUrl, latitude, longitude, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = rootNode.path("result").path("items");

            if (itemsNode.isArray() && !itemsNode.isEmpty()) {
                return itemsNode.get(0).path("full_name").asText("");
            }
            else {
                throw new AddressNotFoundException("Unable to retrieve the address");
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PointDTO getPoint(String address) {
        String url = String.format(Locale.US, "%s?q=%s&fields=items.point&key=%s&locale=ru_KZ",
                baseUrl, address, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = rootNode.path("result").path("items");

            if (itemsNode.isArray() && !itemsNode.isEmpty()) {

                JsonNode pointNode = itemsNode.get(0).path("point");

                return new PointDTO(
                        pointNode.path("lat").asDouble(),
                        pointNode.path("lon").asDouble()
                );
            }
            else {
                throw new AddressNotFoundException("Unable to retrieve the location");
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
