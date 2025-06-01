package com.ecommerce.productservice.repositories;

import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    List<Product> findAllByIdBetween(Long idAfter, Long idBefore);

    List<Product> findAllByTitleEquals(String title);
    
    // upsert
    Product save(Product entity);

    void deleteAll();

    void deleteById(Long productId);

    int deleteAllByTitle(String title);

    int deleteAllByCategory_Title(String categoryTitle);

    @Modifying
    @Query(value = "delete from products p where p.id in (select c.id from categories c where c.id= :categoryId)")
    void deleteProductWhereIdMatchesCategoryId(@Param("categoryId") Long categoryId);

    @Modifying
    @Query(value = "delete from products p where p.createdAt <= :retainDate")
    int retainProductsAfter(Date retainDate);
}
