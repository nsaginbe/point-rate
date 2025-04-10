package org.nurgisa.pointrate.repository;

import org.nurgisa.pointrate.model.RatingCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingCountRepository extends JpaRepository<RatingCount, Long> {
}
