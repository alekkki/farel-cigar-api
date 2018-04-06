package com.farelcigar.api.service;

import com.farelcigar.api.domain.Promotion;
import com.farelcigar.api.domain.dto.PromotionDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PromotionService {

    Promotion createPromotion(PromotionDto promotionDto);

    Promotion addPicture(
            Long promotionId,
            MultipartFile picture) throws IOException;

    List<PromotionDto> getAllPromotions();

    void getPicture(
            Long promotionId,
            HttpServletResponse httpServletResponse) throws IOException;

    Promotion updatePromotion(
            Long id,
            PromotionDto promotionDto);

    void deletePromotion(Long id);
}
