package org.nurgisa.pointrate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RatingServiceTest {

    @Autowired
    RatingService ratingService;

    @Test
    void normalize_WhenCount_ThenScore() {
        int count = 15;
        double score = ratingService.normalize(count);

        System.out.println(score);
    }
}
