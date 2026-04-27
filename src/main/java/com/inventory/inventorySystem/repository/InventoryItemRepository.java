package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.InventoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItemEntity,Long> {
    boolean existsByInventory_IdAndProduct_Id(Long inventoryId, Long productId);

    List<InventoryItemEntity> findAllByInventory_Id(Long inventoryId);
}
