package com.inventory.inventorySystem.dto;

import java.time.LocalDateTime;

public record SupplyResponseDto(
        String productArticle,
        String productName,
        Integer quantity,
        LocalDateTime date,
        String comment
) {
}

