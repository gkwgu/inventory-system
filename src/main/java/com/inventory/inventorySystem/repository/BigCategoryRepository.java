package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.BigCategoryEntity;
import com.inventory.inventorySystem.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BigCategoryRepository extends JpaRepository<BigCategoryEntity,Long> {
    Optional<BigCategoryEntity> findByNameIgnoreCase(String name);
}
