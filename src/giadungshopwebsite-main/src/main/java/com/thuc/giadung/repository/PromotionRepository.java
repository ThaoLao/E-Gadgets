package com.thuc.giadung.repository;

import com.thuc.giadung.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByActiveFlag(boolean activeFlag);

    List<Promotion> findAllByActiveFlag(boolean activeFlag);

    Optional<Promotion> findByTitle(String name);

    @Query(value = "SELECT * FROM promotions p " +
            "WHERE p.start_date <= CURRENT_DATE AND p.end_date >= CURRENT_DATE " +
            "ORDER BY ABS(EXTRACT(DAY FROM (CURRENT_DATE - p.start_date))) ASC, p.id ASC " +
            "LIMIT 1", nativeQuery = true)
    Promotion findClosestActivePromotion();
}
