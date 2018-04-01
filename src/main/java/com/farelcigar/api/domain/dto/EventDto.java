package com.farelcigar.api.domain.dto;

public class EventDto {

    private String title;

    private String location;

    private String description;

    private String date;

    private byte[] data;

    private String filename;

    private String contentType;

    public EventDto() {
    }

    public EventDto(
            String title,
            String location,
            String description,
            String date,
            byte[] data,
            String filename,
            String contentType) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.data = data;
        this.filename = filename;
        this.contentType = contentType;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
