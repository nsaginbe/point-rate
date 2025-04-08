package org.nurgisa.pointrate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.nurgisa.pointrate.dto.PointDTO;
import org.nurgisa.pointrate.dto.RatingDTO;
import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.model.Rating;
import org.nurgisa.pointrate.service.GeocoderService;
import org.nurgisa.pointrate.service.PointService;
import org.nurgisa.pointrate.util.ErrorResponse;
import org.nurgisa.pointrate.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final PointService pointService;
    private final GeocoderService geocoderService;
    private final Mapper mapper;

    @Autowired
    public RatingController(PointService pointService, GeocoderService geocoderService, Mapper mapper) {
        this.pointService = pointService;
        this.geocoderService = geocoderService;
        this.mapper = mapper;
    }

    @PostMapping()
    public ResponseEntity<RatingDTO> getRating(@RequestBody @Valid PointDTO pointDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    ErrorResponse.getErrorMessage(bindingResult)
            );
        }

        Point point = mapper.convertToPoint(pointDTO);
        point.setName(
                geocoderService.getAddress(point.getLatitude(), point.getLongitude())
        );

        Rating rating = pointService.findRatingByPoint(point);

        RatingDTO ratingDTO = mapper.convertToRatingDTO(rating);
        ratingDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(ratingDTO);
    }
}
