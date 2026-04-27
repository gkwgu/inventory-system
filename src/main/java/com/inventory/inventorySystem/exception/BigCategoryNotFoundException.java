package com.inventory.inventorySystem.exception;

public class BigCategoryNotFoundException extends NotFoundException {

    public BigCategoryNotFoundException(String name) {
        super("Категория с названием '" + name + "' не найдена");
    }
}
