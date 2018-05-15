package com.farelcigar.api.service;

import com.farelcigar.api.domain.Brand;
import com.farelcigar.api.domain.BrandFile;
import com.farelcigar.api.domain.enums.FileType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BrandService {

    Brand createBrand(String name);

    List<Brand> getAllBrands();

    Brand updateBrand(Long id, String name);

    void deleteBrand(Long id);

    void addFiles(
            Long brandId,
            MultipartFile picture,
            MultipartFile document) throws IOException;

    void updateFiles(
            Long brandId,
            MultipartFile picture,
            MultipartFile document) throws IOException;

    void addFile(
            Long brandId,
            FileType fileType,
            MultipartFile file) throws IOException;

    void updateFile(
            Long brandId,
            FileType fileType,
            MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getDocument(Long brandId);

    void getPicture(
            Long brandId,
            HttpServletResponse httpServletResponse) throws IOException;

    void deleteFile(Long id);
}
