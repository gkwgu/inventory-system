package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.ReturnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepository extends JpaRepository<ReturnEntity, Long> {
}
