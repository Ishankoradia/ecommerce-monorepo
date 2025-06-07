package com.ecommerce.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@Entity(name="categories")
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseModel {
    String title;
}
