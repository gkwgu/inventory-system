package com.inventory.inventorySystem.dto;

public record InventoryItemResponseDto(
        String productArticle,
        String productName,
        Integer expectedQuantity,
        Integer actualQuantity,
        Integer difference
) {
}