package com.farelcigar.api.service;

import com.farelcigar.api.domain.Event;
import com.farelcigar.api.domain.dto.EventDto;
import com.farelcigar.api.domain.dto.EventPicturesDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EventService {

    Event createEvent(EventDto eventDto);

    Event addPicture(
            Long eventId,
            MultipartFile picture) throws IOException;

    List<EventDto> getAllEvents();

    EventPicturesDto getEventById(Long id);

    void getPicture(
            Long eventId,
            HttpServletResponse httpServletResponse) throws IOException;

    Event updateEvent(
            Long id,
            EventDto eventDto);

    void deleteEvent(Long id);
}
