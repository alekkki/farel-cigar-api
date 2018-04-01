package com.farelcigar.api.repository;

import com.farelcigar.api.domain.BrandFile;
import com.farelcigar.api.domain.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandFileRepository extends JpaRepository<BrandFile, Long> {

    List<BrandFile> findByBrandId(Long brandId);

    Optional<BrandFile> findByBrandIdAndFileType(Long brandId, FileType fileType);
}
