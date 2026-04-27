package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.BigCategoryEntity;
import com.inventory.inventorySystem.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<CategoryEntity> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndBigCategory(String name, BigCategoryEntity bigCategory);
    Optional<CategoryEntity> findByNameIgnoreCaseAndBigCategory(String name, BigCategoryEntity bigCategory);
}
