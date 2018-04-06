package com.farelcigar.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    private String title;

    private String location;

    private String description;

    private LocalDate date;

    @Lob
    private byte[] data;

    private String filename;

    private Integer size;

    @Column(name = "content_type")
    private String contentType;

    public Event() {
    }

    public Event(
            String title,
            String location,
            String description,
            LocalDate date) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.data = null;
        this.filename = null;
        this.size = 0;
        this.contentType = null;
    }

    public Event(
            String title,
            String location,
            String description,
            LocalDate date,
            byte[] data,
            String filename,
            Integer size,
            String contentType) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.data = data;
        this.filename = filename;
        this.size = size;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
