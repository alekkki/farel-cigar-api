package com.farelcigar.api.repository;

import com.farelcigar.api.domain.Brand;
import com.farelcigar.api.domain.enums.BrandType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByBrandTypeOrderByName(BrandType brandType);
}
