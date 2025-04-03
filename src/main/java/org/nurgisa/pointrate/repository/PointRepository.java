package org.nurgisa.pointrate.repository;

import org.nurgisa.pointrate.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByLatitudeAndLongitude(double latitude, double longitude);
}
