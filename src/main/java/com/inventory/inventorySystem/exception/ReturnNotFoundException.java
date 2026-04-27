package com.inventory.inventorySystem.exception;

public class ReturnNotFoundException extends RuntimeException {
    public ReturnNotFoundException(Long id) {
        super("Продажа с id " + id + " не найдена");
    }
}
