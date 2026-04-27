package com.inventory.inventorySystem.exception;


public class CategoryAlreadyExistsException extends ConflictException {
    public CategoryAlreadyExistsException(String name) {
        super("Категория с названием '" + name + "' уже существует");
    }
}