package com.inventory.inventorySystem.dto;

public record SaleCreateDto(
        String productArticle,
        Integer quantity
) {
}
