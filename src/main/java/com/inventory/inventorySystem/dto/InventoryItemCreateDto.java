package com.inventory.inventorySystem.dto;

public record InventoryItemCreateDto(
        String productArticle,
        Integer actualQuantity
) {
}
