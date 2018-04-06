package com.farelcigar.api.service.impl;

import com.farelcigar.api.domain.Event;
import com.farelcigar.api.domain.EventPicture;
import com.farelcigar.api.exception.EntityNotFoundException;
import com.farelcigar.api.repository.EventPictureRepository;
import com.farelcigar.api.repository.EventRepository;
import com.farelcigar.api.service.EventPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class EventPictureServiceImpl implements EventPictureService {

    private final EventRepository eventRepository;
    private final EventPictureRepository eventPictureRepository;
    private final Logger logger = LoggerFactory.getLogger(EventPictureServiceImpl.class);

    public EventPictureServiceImpl(
            EventRepository eventRepository,
            EventPictureRepository eventPictureRepository) {
        this.eventRepository = eventRepository;
        this.eventPictureRepository = eventPictureRepository;
    }

    @Override
    public EventPicture addPicture(
            Long eventId,
            MultipartFile picture) throws IOException {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Event with id [" + eventId + "] not found")
                );

        EventPicture eventPicture = new EventPicture(
                picture.getBytes(),
                picture.getOriginalFilename(),
                toIntExact(picture.getSize()),
                picture.getContentType(),
                event);

        logger.info("Picture added for event with id [{}]", event.getId());
        return eventPictureRepository.save(eventPicture);
    }

    @Override
    public void getPicture(
            Long id,
            HttpServletResponse httpServletResponse) throws IOException {

        EventPicture eventPicture = eventPictureRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Picture with id [" + id + "] not found")
                );

        httpServletResponse.setContentLength(eventPicture.getSize());
        httpServletResponse.setContentType(eventPicture.getContentType());

        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(eventPicture.getData());
        outputStream.flush();
    }

    @Override
    public void deletePicture(Long id) {
        logger.info("Picture with id [{}] deleted", id);
        eventPictureRepository.deleteById(id);
    }

    @Override
    public void deleteAllPicturesForEvent(Long eventId) {
        List<EventPicture> eventPictures = eventPictureRepository.findByEventId(eventId);
        logger.info("Pictures for event with id [{}] deleted", eventId);
        eventPictureRepository.deleteAll(eventPictures);
    }
}
