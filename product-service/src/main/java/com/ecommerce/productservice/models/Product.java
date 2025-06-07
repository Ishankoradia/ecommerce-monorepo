package com.ecommerce.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity(name="products")
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseModel {
    private String title;
    private Double price;
    private String description;
    private String image;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
}
