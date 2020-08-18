package com.Snowhite.exception;

public class ProductExistException extends RuntimeException {

    public ProductExistException() {
        super("Product exist, please choose another");
    }
}
