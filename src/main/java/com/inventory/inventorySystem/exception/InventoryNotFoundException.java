package com.inventory.inventorySystem.exception;

public class InventoryNotFoundException extends NotFoundException {

    public InventoryNotFoundException(Long id) {
        super("Инвентаризация с id = " + id + " не найдена");
    }
}
