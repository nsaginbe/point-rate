package org.nurgisa.pointrate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PointDTO {
    @NotNull(message = "Latitude can not be null")
    private Double latitude;

    @NotNull(message = "Longitude can not be null")
    private Double longitude;

    // Can be null in request
    // Include in response only if not null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timestamp;
}
