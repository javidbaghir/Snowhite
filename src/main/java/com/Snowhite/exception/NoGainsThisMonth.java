package com.Snowhite.exception;

public class NoGainsThisMonth extends RuntimeException {

    public NoGainsThisMonth() {
        super("No gains were made this month");
    }
}
