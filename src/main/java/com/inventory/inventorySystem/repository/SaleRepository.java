package com.inventory.inventorySystem.repository;

import com.inventory.inventorySystem.model.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity,Long> {
    List<SaleEntity> findByDateBetween(LocalDateTime start, LocalDateTime end);
}
