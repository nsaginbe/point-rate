package org.nurgisa.pointrate.service;

import org.nurgisa.pointrate.client.GisApiClient;
import org.nurgisa.pointrate.client.IQAirApiClient;
import org.nurgisa.pointrate.model.Rating;
import org.nurgisa.pointrate.model.RatingCount;
import org.nurgisa.pointrate.model.ScoreType;
import org.nurgisa.pointrate.repository.RatingCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final GisApiClient gisApiClient;
    private final IQAirApiClient iqAirApiClient;

    @Autowired
    public RatingService(GisApiClient gisApiClient, IQAirApiClient iqAirApiClient) {
        this.gisApiClient = gisApiClient;
        this.iqAirApiClient = iqAirApiClient;
    }

    public Rating calculateRating(double lat, double lon) {
        Rating rating = new Rating();
        RatingCount ratingCount = new RatingCount();

        double airQualityScore = iqAirApiClient.getAirQualityScore();

        // TODO(ADD TRANSPORT)
        ScoreType[] types = {ScoreType.EDUCATION, ScoreType.SPORTS,
                ScoreType.ENTERTAINMENT, ScoreType.ECOLOGY};

        for (ScoreType type : types) {
            int count = gisApiClient.counter(lat, lon, type);

            double score = (count == 0) ? 0 : normalize(count);

            if (type == ScoreType.ECOLOGY) {
                score = (score + airQualityScore) / 2.0;
            }

            setRatingScore(rating, type, score);
            setRatingCount(ratingCount, type, count);
        }
        setOverallScore(rating);

        pair(rating, ratingCount);

        return rating;
    }

    private void setRatingScore(Rating rating, ScoreType type, double score) {
        switch (type) {
            case EDUCATION -> rating.setEducation(score);
            case SPORTS -> rating.setSports(score);
            case ENTERTAINMENT -> rating.setEntertainment(score);
            case TRANSPORT -> rating.setTransport(score);
            case ECOLOGY -> rating.setEcology(score);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void setRatingCount(RatingCount ratingCount, ScoreType type, int count) {
        switch (type) {
            case EDUCATION -> ratingCount.setEducation(count);
            case SPORTS -> ratingCount.setSports(count);
            case ENTERTAINMENT -> ratingCount.setEntertainment(count);
            case TRANSPORT -> ratingCount.setTransport(count);
            case ECOLOGY -> ratingCount.setEcology(count);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void setOverallScore(Rating rating) {
        double total = 0.0;
        int number = ScoreType.values().length;

        total += rating.getEducation();
        total += rating.getSports();
        total += rating.getEntertainment();
        total += rating.getTransport();
        total += rating.getEcology();

        double overallScore = total / number;
        rating.setOverall(overallScore);
    }

    private void pair(Rating rating, RatingCount ratingCount) {
        rating.setRatingCount(ratingCount);
        ratingCount.setRating(rating);
    }

    public static double normalize(int count) {
        double min = 5.0;
        double max = 10.0;
        double steepness = 0.5;
        double shift = 5.0;

        double logisticPart = 1 / (1 + Math.exp(-steepness * (count - shift)));
        double score = min + (max - min) * logisticPart;

        return Math.round(score * 100.0) / 100.0;
    }
}
