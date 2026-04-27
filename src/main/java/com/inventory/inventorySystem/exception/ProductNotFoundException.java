package com.inventory.inventorySystem.exception;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException(String article) {
        super("Товар с артикулом '" + article + "' не найден");
    }
}
