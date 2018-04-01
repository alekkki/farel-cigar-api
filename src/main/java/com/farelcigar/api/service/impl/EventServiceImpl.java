package com.farelcigar.api.service.impl;

import com.farelcigar.api.domain.Event;
import com.farelcigar.api.domain.EventPicture;
import com.farelcigar.api.domain.dto.EventDto;
import com.farelcigar.api.domain.dto.EventPicturesDto;
import com.farelcigar.api.exception.EntityNotFoundException;
import com.farelcigar.api.repository.EventPictureRepository;
import com.farelcigar.api.repository.EventRepository;
import com.farelcigar.api.service.EventService;
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

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventPictureRepository eventPictureRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    public EventServiceImpl(
            EventRepository eventRepository,
            EventPictureRepository eventPictureRepository) {
        this.eventRepository = eventRepository;
        this.eventPictureRepository = eventPictureRepository;
    }

    @Override
    public Event createEvent(EventDto eventDto) {

        Event event = new Event(
                eventDto.getTitle(),
                eventDto.getLocation(),
                eventDto.getDescription(),
                LocalDateTime.parse(eventDto.getDate(), formatter),
                eventDto.getData(),
                eventDto.getFilename(),
                eventDto.getData().length,
                eventDto.getContentType());

        logger.info("Event with title [{}] created", event.getTitle());
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public EventPicturesDto getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Event with id [" + id + "] not found")
                );

        List<Long> eventPicturesIds = new ArrayList<>();
        List<EventPicture> eventPictures = eventPictureRepository.findByEventId(id);
        for (EventPicture eventPicture : eventPictures) {
            eventPicturesIds.add(eventPicture.getId());
        }

        return new EventPicturesDto(
                event.getTitle(),
                event.getLocation(),
                event.getDescription(),
                event.getDate().toString(),
                eventPicturesIds);
    }

    @Override
    public Event updateEvent(Long id, EventDto eventDto) {

        return eventRepository.findById(id)
                .map(event -> {
                    event.setTitle(eventDto.getTitle());
                    event.setLocation(eventDto.getLocation());
                    event.setDescription(eventDto.getDescription());
                    event.setDate(LocalDateTime.parse(eventDto.getDate(), formatter));
                    event.setData(eventDto.getData());
                    event.setFilename(eventDto.getFilename());
                    event.setSize(eventDto.getData().length);
                    event.setContentType(eventDto.getContentType());
                    logger.info("Event with id [{}] updated", id);
                    return eventRepository.save(event);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("Event with id [" + id + "] not found")
                );
    }

    @Override
    public void deleteEvent(Long id) {
        List<EventPicture> eventPictures = eventPictureRepository.findByEventId(id);
        logger.info("Pictures for event with id [{}] deleted", id);
        eventPictureRepository.deleteAll(eventPictures);

        logger.info("Event with id [{}] deleted", id);
        eventRepository.deleteById(id);
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
                picture.getBytes().length,
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
