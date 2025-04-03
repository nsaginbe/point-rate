package org.nurgisa.pointrate.service;

import org.nurgisa.pointrate.model.Rating;
import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating calculateRating(Point point) {
        Rating rating = new Rating();

        rating.setEducation(1.5);
        rating.setSports(1.5);
        rating.setEntertainment(1.5);
        rating.setTransport(1.5);
        rating.setEcology(1.5);

        rating.setOverall(1.5);

        return rating;
    }

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }
}
