package com.ecommerce.productservice.repositories;

import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long productId);

    List<Product> findByTitleContainsIgnoreCase(String title);

    List<Product> findByPriceBetween(Double priceAfter, Double priceBefore);

    List<Product> findByCategory(Category category);

    List<Product> findByCategory_Id(Long categoryId);
    
    List<Product> findByCategory_Title(String categoryTitle);

    @Query("select title from products where id = ?")
    Optional<Product> findOnlyProductTitleById(Long productId);
}
