package com.inventory.inventorySystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public record InventoryResponseDto(
        Long id,
        LocalDateTime date,
        String status,
        List<InventoryItemResponseDto> items
) {
}
