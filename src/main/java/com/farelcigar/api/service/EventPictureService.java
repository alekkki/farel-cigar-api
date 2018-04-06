package com.farelcigar.api.service;

import com.farelcigar.api.domain.EventPicture;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface EventPictureService {

    EventPicture addPicture(
            Long eventId,
            MultipartFile picture) throws IOException;

    void getPicture(
            Long id,
            HttpServletResponse httpServletResponse) throws IOException;

    void deletePicture(Long id);

    void deleteAllPicturesForEvent(Long eventId);
}
