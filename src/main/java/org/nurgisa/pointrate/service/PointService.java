package org.nurgisa.pointrate.service;

import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.model.Rating;
import org.nurgisa.pointrate.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PointService {

    private final PointRepository pointRepository;
    private final RatingService ratingService;

    @Autowired
    public PointService(PointRepository pointRepository, RatingService ratingService) {
        this.pointRepository = pointRepository;
        this.ratingService = ratingService;
    }

    @Transactional
    public void save(Point point) {
        pointRepository.save(point);
    }

    @Transactional
    public Rating findRatingByPoint(Point point) {
        Optional<Point> existingPoint = pointRepository.findByLatitudeAndLongitude(
                point.getLatitude(), point.getLongitude()
        );

        if (existingPoint.isPresent()) {
            Rating existingRating = existingPoint.get().getRating();

            if (existingRating != null) {
                return existingRating;
            }
        }

        Rating rating = ratingService.calculateRating(
                point.getLatitude(),
                point.getLongitude()
        );
        rating.setPoint(point);
        point.setRating(rating);

        save(point);

        return rating;
    }

    @Transactional(readOnly = true)
    public List<Point> findAll() {
        return pointRepository.findAll();
    }
}
