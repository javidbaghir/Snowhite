package com.Snowhite.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long id) {
        super(String.format("Product with Id %d not found", id));
    }
}
