package org.nurgisa.pointrate.util;

import org.modelmapper.ModelMapper;
import org.nurgisa.pointrate.dto.PointDTO;
import org.nurgisa.pointrate.dto.RatingDTO;
import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Point convertToPoint(PointDTO pointDTO) {
        return modelMapper.map(pointDTO, Point.class);
    }

    public PointDTO convertToPointDTO(Point point) {
        return modelMapper.map(point, PointDTO.class);
    }

    public Rating convertToRating(RatingDTO ratingDTO) {
        return modelMapper.map(ratingDTO, Rating.class);
    }

    public RatingDTO convertToRatingDTO(Rating rating) {
        return modelMapper.map(rating, RatingDTO.class);
    }
}
