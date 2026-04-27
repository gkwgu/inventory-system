package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {
    List<InventoryEntity> findAllByOrderByDateDesc();
}
