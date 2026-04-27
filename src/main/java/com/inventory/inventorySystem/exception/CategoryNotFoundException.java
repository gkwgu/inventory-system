package com.inventory.inventorySystem.exception;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(String name) {
        super("Категория с названием '" + name + "' не найдена");
    }
}