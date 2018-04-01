package com.farelcigar.api.domain.dto;

import java.util.List;

public class EventPicturesDto {

    private String title;

    private String location;

    private String description;

    private String date;

    private List<Long> eventPicturesIds;

    public EventPicturesDto() {
    }

    public EventPicturesDto(
            String title,
            String location,
            String description,
            String date,
            List<Long> eventPicturesIds) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.eventPicturesIds = eventPicturesIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Long> getEventPicturesIds() {
        return eventPicturesIds;
    }

    public void setEventPicturesIds(List<Long> eventPicturesIds) {
        this.eventPicturesIds = eventPicturesIds;
    }
}
