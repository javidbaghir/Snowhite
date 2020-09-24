package com.Snowhite.exception;

public class NoSalePriceException extends RuntimeException {
    public NoSalePriceException() {
        super("No sale price, please write the selling price");
    }
}
