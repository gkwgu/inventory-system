package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.SupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<SupplyEntity,Long> {
}
