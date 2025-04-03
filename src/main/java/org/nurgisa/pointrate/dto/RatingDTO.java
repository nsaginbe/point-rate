package org.nurgisa.pointrate.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nurgisa.pointrate.model.Point;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {
    private PointDTO point;
    private double education;
    private double sports;
    private double entertainment;
    private double transport;
    private double ecology;
    private double overall;
    private LocalDateTime timestamp;
}
