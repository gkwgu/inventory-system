package com.inventory.inventorySystem.dto;

import java.math.BigDecimal;

public record ProductCreateDto(
        String article,
        String name,
        BigDecimal price,
        String categoryName
) {
}
