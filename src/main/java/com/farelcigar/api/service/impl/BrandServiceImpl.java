package com.farelcigar.api.service.impl;

import com.farelcigar.api.domain.Brand;
import com.farelcigar.api.domain.BrandFile;
import com.farelcigar.api.domain.enums.FileType;
import com.farelcigar.api.exception.EntityNotFoundException;
import com.farelcigar.api.repository.BrandFileRepository;
import com.farelcigar.api.repository.BrandRepository;
import com.farelcigar.api.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandFileRepository brandFileRepository;
    private final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    public BrandServiceImpl(
            BrandRepository brandRepository,
            BrandFileRepository brandFileRepository) {
        this.brandRepository = brandRepository;
        this.brandFileRepository = brandFileRepository;
    }

    @Override
    public Brand createBrand(String name) {
        Brand brand = new Brand(name);
        logger.info("Brand with name [{}] created", brand.getName());
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand updateBrand(Long id, String name) {
        return brandRepository.findById(id)
                .map(brand -> {
                    brand.setName(name);
                    logger.info("Brand with id [{}] updated", id);
                    return brandRepository.save(brand);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("Brand with id [" + id + "] not found")
                );
    }

    @Override
    public void deleteBrand(Long id) {
        List<BrandFile> brandFiles = brandFileRepository.findByBrandId(id);
        logger.info("Files for brand with id [{}] deleted", id);
        brandFileRepository.deleteAll(brandFiles);

        logger.info("Brand with id [{}] deleted", id);
        brandRepository.deleteById(id);
    }

    @Override
    public void addFiles(
            Long brandId,
            MultipartFile picture,
            MultipartFile document) throws IOException {

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Brand with id [" + brandId + "] not found")
                );

        if (picture != null) {
            BrandFile pictureFile = new BrandFile(
                    picture.getBytes(),
                    picture.getOriginalFilename(),
                    toIntExact(picture.getSize()),
                    picture.getContentType(),
                    FileType.PICTURE,
                    brand);

            logger.info("Picture added for brand with id [{}]", brandId);
            brandFileRepository.save(pictureFile);
        }

        if (document != null) {
            BrandFile documentFile = new BrandFile(
                    document.getBytes(),
                    document.getOriginalFilename(),
                    toIntExact(document.getSize()),
                    document.getContentType(),
                    FileType.DOCUMENT,
                    brand);

            logger.info("Document added for brand with id [{}]", brandId);
            brandFileRepository.save(documentFile);
        }
    }

    @Override
    public void updateFiles(
            Long brandId,
            MultipartFile picture,
            MultipartFile document) throws IOException {

        if (picture != null) {
            byte[] pictureData = picture.getBytes();
            int pictureSize = toIntExact(picture.getSize());
            brandFileRepository.findByBrandIdAndFileType(brandId, FileType.PICTURE)
                    .map(brandFile -> {
                        brandFile.setData(pictureData);
                        brandFile.setName(picture.getOriginalFilename());
                        brandFile.setSize(pictureSize);
                        brandFile.setContentType(picture.getContentType());
                        logger.info("Picture for brand with id [{}] updated", brandId);
                        return brandFileRepository.save(brandFile);
                    })
                    .orElseThrow(() ->
                            new EntityNotFoundException("File for brand with id [" + brandId + "] not found")
                    );
        }

        if (document != null) {
            byte[] documentData = document.getBytes();
            int documentSize = toIntExact(document.getSize());
            brandFileRepository.findByBrandIdAndFileType(brandId, FileType.DOCUMENT)
                    .map(brandFile -> {
                        brandFile.setData(documentData);
                        brandFile.setName(document.getOriginalFilename());
                        brandFile.setSize(documentSize);
                        brandFile.setContentType(document.getContentType());
                        logger.info("Document for brand with id [{}] updated", brandId);
                        return brandFileRepository.save(brandFile);
                    })
                    .orElseThrow(() ->
                            new EntityNotFoundException("File for brand with id [" + brandId + "] not found")
                    );
        }
    }

    @Override
    public void addFile(
            Long brandId,
            FileType fileType,
            MultipartFile file) throws IOException {

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Brand with id [" + brandId + "] not found")
                );

        if (file != null) {
            BrandFile brandFile = new BrandFile(
                    file.getBytes(),
                    file.getOriginalFilename(),
                    toIntExact(file.getSize()),
                    file.getContentType(),
                    fileType,
                    brand);

            logger.info("File added for brand with id [{}]", brandId);
            brandFileRepository.save(brandFile);
        }
    }

    @Override
    public void updateFile(
            Long brandId,
            FileType fileType,
            MultipartFile file) throws IOException {

        if (file != null) {
            byte[] data = file.getBytes();
            int size = toIntExact(file.getSize());
            brandFileRepository.findByBrandIdAndFileType(brandId, fileType)
                    .map(brandFile -> {
                        brandFile.setData(data);
                        brandFile.setName(file.getOriginalFilename());
                        brandFile.setSize(size);
                        brandFile.setContentType(file.getContentType());
                        logger.info("File for brand with id [{}] updated", brandId);
                        return brandFileRepository.save(brandFile);
                    })
                    .orElseThrow(() ->
                            new EntityNotFoundException("File for brand with id [" + brandId + "] not found")
                    );
        }
    }

    @Override
    public ResponseEntity<byte[]> getDocument(Long brandId) {

        BrandFile brandFile = brandFileRepository.findByBrandIdAndFileType(brandId, FileType.DOCUMENT)
                .orElseThrow(() ->
                        new EntityNotFoundException("File for brand with id [" + brandId + "] not found")
                );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(brandFile.getSize());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.setContentType(MediaType.parseMediaType(brandFile.getContentType()));

        String filename = brandFile.getName();
        headers.add("content-disposition", "inline;filename=" + filename);
        return new ResponseEntity<>(brandFile.getData(), headers, HttpStatus.OK);
    }

    @Override
    public void getPicture(
            Long brandId,
            HttpServletResponse httpServletResponse) throws IOException {

        BrandFile brandFile = brandFileRepository.findByBrandIdAndFileType(brandId, FileType.PICTURE)
                .orElseThrow(() ->
                        new EntityNotFoundException("File for brand with id [" + brandId + "] not found")
                );

        httpServletResponse.setContentLength(brandFile.getSize());
        httpServletResponse.setContentType(brandFile.getContentType());

        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(brandFile.getData());
        outputStream.flush();
    }

    @Override
    public void deleteFile(Long id) {
        logger.info("File with id [{}] deleted", id);
        brandFileRepository.deleteById(id);
    }
}
