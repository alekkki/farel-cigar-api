package com.farelcigar.api.service;

import com.farelcigar.api.domain.Event;
import com.farelcigar.api.domain.EventPicture;
import com.farelcigar.api.domain.dto.EventDto;
import com.farelcigar.api.domain.dto.EventPicturesDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EventService {

    Event createEvent(EventDto eventDto);

    List<Event> getAllEvents();

    EventPicturesDto getEventById(Long id);

    Event updateEvent(Long id, EventDto eventDto);

    void deleteEvent(Long id);

    EventPicture addPicture(
            Long eventId,
            MultipartFile picture) throws IOException;

    void getPicture(
            Long id,
            HttpServletResponse httpServletResponse) throws IOException;

    void deletePicture(Long id);

    void deleteAllPicturesForEvent(Long eventId);
}
