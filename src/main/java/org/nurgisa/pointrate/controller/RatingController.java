package org.nurgisa.pointrate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.nurgisa.pointrate.dto.PointDTO;
import org.nurgisa.pointrate.dto.RatingDTO;
import org.nurgisa.pointrate.exception.AddressNotFoundException;
import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.model.Rating;
import org.nurgisa.pointrate.service.GeocoderService;
import org.nurgisa.pointrate.service.PointService;
import org.nurgisa.pointrate.service.RatingService;
import org.nurgisa.pointrate.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;
    private final PointService pointService;
    private final GeocoderService geocoderService;
    private final ModelMapper modelMapper;

    @Autowired
    public RatingController(RatingService ratingService, PointService pointService, GeocoderService geocoderService, ModelMapper modelMapper) {
        this.ratingService = ratingService;
        this.pointService = pointService;
        this.geocoderService = geocoderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<RatingDTO> getRating(@RequestBody @Valid PointDTO pointDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    ErrorResponse.getErrorMessage(bindingResult)
            );
        }

        Point point = convertToPoint(pointDTO);
        point.setName(
                geocoderService.getAddress(point.getLatitude(), point.getLongitude())
        );

        Rating rating = pointService.findRatingByPoint(point);

        RatingDTO ratingDTO = convertToRatingDTO(rating);

        ratingDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(ratingDTO);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return ResponseEntity.badRequest()
                .body(response);
    }

    private Point convertToPoint(PointDTO pointDTO) {
        return modelMapper.map(pointDTO, Point.class);
    }

    private RatingDTO convertToRatingDTO(Rating rating) {
        return modelMapper.map(rating, RatingDTO.class);
    }
}
