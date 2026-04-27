package com.inventory.inventorySystem.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        String article,
        String name,
        BigDecimal price,
        Integer quantity,
        String categoryName
) {
}