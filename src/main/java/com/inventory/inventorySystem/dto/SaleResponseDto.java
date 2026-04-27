package com.inventory.inventorySystem.dto;

import java.time.LocalDateTime;

public record SaleResponseDto(
        Long id,
        String productArticle,
        String productName,
        Integer quantity,
        LocalDateTime date
) {
}