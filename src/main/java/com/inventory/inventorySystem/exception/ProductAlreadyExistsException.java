package com.inventory.inventorySystem.exception;

public class ProductAlreadyExistsException extends ConflictException {

    public ProductAlreadyExistsException(String article) {
        super("Товар с артикулом '" + article + "' уже существует");
    }
}
