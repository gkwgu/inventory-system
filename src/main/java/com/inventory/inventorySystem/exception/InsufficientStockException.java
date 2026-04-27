package com.inventory.inventorySystem.exception;

public class InsufficientStockException extends ConflictException {

    public InsufficientStockException(String article) {
        super("Недостаточно товара с артикулом '" + article + "' на складе");
    }
}
