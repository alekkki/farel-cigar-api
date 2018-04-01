package com.farelcigar.api.domain;

import javax.persistence.*;

@Entity
@Table(name = "event_pictures")
public class EventPicture extends BaseEntity {

    @Lob
    private byte[] data;

    private String name;

    private Integer size;

    @Column(name = "content_type")
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public EventPicture() {
    }

    public EventPicture(
            byte[] data,
            String name,
            Integer size,
            String contentType,
            Event event) {
        this.data = data;
        this.name = name;
        this.size = size;
        this.contentType = contentType;
        this.event = event;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
