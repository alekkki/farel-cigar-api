package com.farelcigar.api.service.impl;

import com.farelcigar.api.domain.Promotion;
import com.farelcigar.api.domain.dto.PromotionDto;
import com.farelcigar.api.exception.EntityNotFoundException;
import com.farelcigar.api.repository.PromotionRepository;
import com.farelcigar.api.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Promotion createPromotion(PromotionDto promotionDto) {

        Promotion promotion = new Promotion(
                promotionDto.getTitle(),
                promotionDto.getDescription(),
                LocalDateTime.parse(promotionDto.getStartDate(), formatter),
                LocalDateTime.parse(promotionDto.getEndDate(), formatter));

        logger.info("Promotion with title [{}] created", promotion.getTitle());
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion addPicture(
            Long promotionId,
            MultipartFile picture) throws IOException {

        byte[] data = picture.getBytes();
        int size = toIntExact(picture.getSize());

        return promotionRepository.findById(promotionId)
                .map(promotion -> {
                    promotion.setData(data);
                    promotion.setFilename(picture.getOriginalFilename());
                    promotion.setSize(size);
                    promotion.setContentType(picture.getContentType());
                    logger.info("Picture added for promotion with id [{}]", promotionId);
                    return promotionRepository.save(promotion);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("Promotion with id [" + promotionId + "] not found")
                );
    }

    @Override
    public List<PromotionDto> getAllPromotions() {
        List<PromotionDto> promotionDtoList = new ArrayList<>();
        List<Promotion> promotions = promotionRepository.findAll();
        for (Promotion p : promotions) {
            PromotionDto promotionDto = new PromotionDto(
                    p.getId(),
                    p.getTitle(),
                    p.getDescription(),
                    p.getStartDate().toString(),
                    p.getEndDate().toString());
            promotionDtoList.add(promotionDto);
        }
        return promotionDtoList;
    }

    @Override
    public Promotion updatePromotion(
            Long id,
            PromotionDto promotionDto) {

        return promotionRepository.findById(id)
                .map(promotion -> {
                    promotion.setTitle(promotionDto.getTitle());
                    promotion.setDescription(promotionDto.getDescription());
                    promotion.setStartDate(LocalDateTime.parse(promotionDto.getStartDate(), formatter));
                    promotion.setEndDate(LocalDateTime.parse(promotionDto.getEndDate(), formatter));
                    logger.info("Promotion with id [{}] updated", id);
                    return promotionRepository.save(promotion);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("Promotion with id [" + id + "] not found")
                );
    }

    @Override
    public void deletePromotion(Long id) {
        logger.info("Promotion with id [{}] deleted", id);
        promotionRepository.deleteById(id);
    }

    @Override
    public void getPicture(
            Long id,
            HttpServletResponse httpServletResponse) throws IOException {

        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Promotion with id [" + id + "] not found")
                );

        httpServletResponse.setContentLength(promotion.getSize());
        httpServletResponse.setContentType(promotion.getContentType());

        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(promotion.getData());
        outputStream.flush();
    }
}
