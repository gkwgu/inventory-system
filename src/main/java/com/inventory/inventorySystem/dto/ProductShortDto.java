package com.inventory.inventorySystem.dto;

import java.math.BigDecimal;

public record ProductShortDto(
        String article,
        String name,
        BigDecimal price,
        Integer quantity
) {
}
