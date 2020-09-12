package com.Snowhite.exception;

public class PasswordDoNotMatchException extends RuntimeException {

    public PasswordDoNotMatchException() {
        super("Password don't match");
    }
}
