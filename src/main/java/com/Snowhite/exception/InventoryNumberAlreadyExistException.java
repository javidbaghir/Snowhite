package com.Snowhite.exception;

public class InventoryNumberAlreadyExistException extends RuntimeException {
    public InventoryNumberAlreadyExistException() {
        super("Inventory number already exist");
    }
}
