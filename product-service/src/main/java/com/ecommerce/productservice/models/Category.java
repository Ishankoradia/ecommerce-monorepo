package com.ecommerce.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name="categories")
public class Category extends BaseModel {
    String title;

    @OneToMany(mappedBy = "category")
    // same name as the relation attribute name in Product entity
    private List<Product> products;
}
