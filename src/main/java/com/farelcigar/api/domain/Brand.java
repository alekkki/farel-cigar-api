package com.farelcigar.api.domain;

import com.farelcigar.api.domain.enums.BrandType;

import javax.persistence.*;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand_type")
    private BrandType brandType;

    public Brand() {
    }

    public Brand(String name, BrandType brandType) {
        this.name = name;
        this.brandType = brandType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrandType getBrandType() {
        return brandType;
    }

    public void setBrandType(BrandType brandType) {
        this.brandType = brandType;
    }
}
