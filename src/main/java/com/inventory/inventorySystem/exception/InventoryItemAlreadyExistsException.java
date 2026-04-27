package com.inventory.inventorySystem.exception;

public class InventoryItemAlreadyExistsException extends ConflictException {

    public InventoryItemAlreadyExistsException(String article) {
        super("Товар '" + article + "' уже добавлен в инвентаризацию");
    }
}
