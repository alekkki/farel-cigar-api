package com.farelcigar.api.domain;

import com.farelcigar.api.domain.enums.FileType;

import javax.persistence.*;

@Entity
@Table(name = "brand_files")
public class BrandFile extends BaseEntity {

    @Lob
    private byte[] data;

    private String name;

    private Integer size;

    @Column(name = "content_type")
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public BrandFile() {
    }

    public BrandFile(
            byte[] data,
            String name,
            Integer size,
            String contentType,
            FileType fileType,
            Brand brand) {
        this.data = data;
        this.name = name;
        this.size = size;
        this.contentType = contentType;
        this.fileType = fileType;
        this.brand = brand;
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

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}