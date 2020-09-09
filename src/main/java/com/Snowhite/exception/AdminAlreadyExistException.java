package com.Snowhite.exception;

public class AdminAlreadyExistException extends RuntimeException {

    public AdminAlreadyExistException() {
        super("Admin already exist");
    }
}
