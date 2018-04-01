package com.farelcigar.api.service.impl;

import com.farelcigar.api.domain.Promotion;
import com.farelcigar.api.domain.dto.PromotionDto;
import com.farelcigar.api.exception.EntityNotFoundException;
import com.farelcigar.api.repository.PromotionRepository;
import com.farelcigar.api.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                LocalDateTime.parse(promotionDto.getEndDate(), formatter),
                promotionDto.getData(),
                promotionDto.getFilename(),
                promotionDto.getData().length,
                promotionDto.getContentType());

        logger.info("Promotion with title [{}] created", promotion.getTitle());
        return promotionRepository.save(promotion);
    }

    @Override
    public List<PromotionDto> getAllPromotions() {
        List<PromotionDto> promotionDtoList = new ArrayList<>();
        List<Promotion> promotions = promotionRepository.findAll();
        for (Promotion promotion : promotions) {
            PromotionDto p = new PromotionDto(
                    promotion.getTitle(),
                    promotion.getDescription(),
                    promotion.getStartDate().toString(),
                    promotion.getEndDate().toString(),
                    promotion.getData(),
                    promotion.getFilename(),
                    promotion.getSize(),
                    promotion.getContentType());
            promotionDtoList.add(p);
        }
        return promotionDtoList;
    }

    @Override
    public Promotion updatePromotion(Long id, PromotionDto promotionDto) {

        return promotionRepository.findById(id)
                .map(promotion -> {
                    promotion.setTitle(promotionDto.getTitle());
                    promotion.setDescription(promotionDto.getDescription());
                    promotion.setStartDate(LocalDateTime.parse(promotionDto.getStartDate(), formatter));
                    promotion.setEndDate(LocalDateTime.parse(promotionDto.getEndDate(), formatter));
                    promotion.setData(promotionDto.getData());
                    promotion.setFilename(promotion.getFilename());
                    promotion.setSize(promotionDto.getData().length);
                    promotion.setContentType(promotion.getContentType());
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
