package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByArticle(String article);

    boolean existsByArticle(String article);

    void deleteByArticle(String article);

    List<ProductEntity> findAllByCategory_Id(Long categoryId);
}
