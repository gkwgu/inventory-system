package com.inventory.inventorySystem.dto;

public record SupplyCreateDto(
        String productArticle,
        Integer quantity,
        String comment
) {
}
