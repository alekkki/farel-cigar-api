package com.farelcigar.api.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    public Brand() {
    }

    public Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
