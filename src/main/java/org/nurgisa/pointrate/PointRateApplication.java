package org.nurgisa.pointrate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PointRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointRateApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	@Qualifier("gis.places")
	WebClient gisWebClient(WebClient.Builder builder,
						@Value("${gis.places.base-url}") String baseUrl) {

		return builder.baseUrl(baseUrl).build();
	}

	@Bean
	@Qualifier("iqair.api")
	WebClient iqAirWebClient(WebClient.Builder builder,
						@Value("${iqair.api.base-url}") String baseUrl) {

		return builder.baseUrl(baseUrl).build();
	}
}
