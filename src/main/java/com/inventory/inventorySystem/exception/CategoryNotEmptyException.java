package com.inventory.inventorySystem.exception;

public class CategoryNotEmptyException extends ConflictException {

    public CategoryNotEmptyException(String categoryName) {
        super("Невозможно удалить категорию '" + categoryName + "', так как в ней есть товары");
    }
}
