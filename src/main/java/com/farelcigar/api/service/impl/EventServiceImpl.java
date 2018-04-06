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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventPictureRepository eventPictureRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
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
                LocalDate.parse(eventDto.getDate(), formatter));

        logger.info("Event with title [{}] created", event.getTitle());
        return eventRepository.save(event);
    }

    @Override
    public Event addPicture(
            Long eventId,
            MultipartFile picture) throws IOException {

        byte[] data = picture.getBytes();
        int size = toIntExact(picture.getSize());

        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setData(data);
                    event.setFilename(picture.getOriginalFilename());
                    event.setSize(size);
                    event.setContentType(picture.getContentType());
                    logger.info("Picture added for event with id [{}]", eventId);
                    return eventRepository.save(event);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("Event with id [" + eventId + "] not found")
                );
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<EventDto> eventDtoList = new ArrayList<>();
        List<Event> events = eventRepository.findAll();
        for (Event e : events) {
            EventDto eventDto = new EventDto(
                    e.getId(),
                    e.getTitle(),
                    e.getLocation(),
                    e.getDescription(),
                    e.getDate().toString());
            eventDtoList.add(eventDto);
        }
        return eventDtoList;
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
    public void getPicture(
            Long eventId,
            HttpServletResponse httpServletResponse) throws IOException {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Event with id [" + eventId + "] not found")
                );

        httpServletResponse.setContentLength(event.getSize());
        httpServletResponse.setContentType(event.getContentType());

        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(event.getData());
        outputStream.flush();
    }

    @Override
    public Event updateEvent(Long id, EventDto eventDto) {

        return eventRepository.findById(id)
                .map(event -> {
                    event.setTitle(eventDto.getTitle());
                    event.setLocation(eventDto.getLocation());
                    event.setDescription(eventDto.getDescription());
                    event.setDate(LocalDate.parse(eventDto.getDate(), formatter));
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
}
