package com.farelcigar.api.domain.dto;

public class EventDto {

    private Long id;

    private String title;

    private String location;

    private String description;

    private String date;

    public EventDto() {
    }

    public EventDto(
            Long id,
            String title,
            String location,
            String description,
            String date) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
