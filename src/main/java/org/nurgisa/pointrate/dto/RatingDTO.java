package org.nurgisa.pointrate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
