package com.farelcigar.api.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EventPictureService {

    void addPicture(
            Long eventId,
            MultipartFile picture) throws IOException;

    void getPicture(
            Long id,
            HttpServletResponse httpServletResponse) throws IOException;

    void updatePictures(
            Long eventId,
            MultipartFile[] pictures) throws IOException;

    void deletePicture(Long id);

    void deleteAllPicturesForEvent(Long eventId);
}
