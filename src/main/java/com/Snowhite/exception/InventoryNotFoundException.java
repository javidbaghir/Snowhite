package com.Snowhite.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(long id) {
        super(String.format("Inventory with Id %d not found", id));
    }
}
