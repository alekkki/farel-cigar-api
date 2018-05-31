package com.farelcigar.api.repository;

import com.farelcigar.api.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findAllByOrderByEndDateAsc();
}
