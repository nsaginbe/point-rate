package org.nurgisa.pointrate.repository;

import org.nurgisa.pointrate.model.Rating;
import org.nurgisa.pointrate.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByPoint(Point point);
}
