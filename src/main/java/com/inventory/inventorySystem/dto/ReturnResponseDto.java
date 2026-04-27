package com.inventory.inventorySystem.dto;

import java.time.LocalDateTime;

public record ReturnResponseDto(
        String productArticle,
        String productName,
        Integer quantity,
        LocalDateTime date,
        String reason
) {
}
