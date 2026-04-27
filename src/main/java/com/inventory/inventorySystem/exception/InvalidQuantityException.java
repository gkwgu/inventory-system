package com.inventory.inventorySystem.exception;

public class InvalidQuantityException extends BadRequestException {

    public InvalidQuantityException() {
        super("Количество должно быть положительным числом");
    }
}
