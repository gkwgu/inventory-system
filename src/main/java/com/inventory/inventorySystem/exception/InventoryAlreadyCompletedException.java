package com.inventory.inventorySystem.exception;

public class InventoryAlreadyCompletedException extends ConflictException {

    public InventoryAlreadyCompletedException(Long inventoryId) {
        super("Инвентаризация с id = " + inventoryId + " уже завершена");
    }
}
