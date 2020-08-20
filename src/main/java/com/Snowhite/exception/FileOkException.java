package com.Snowhite.exception;

public class FileOkException extends RuntimeException {

    public FileOkException() {
        super("Image must be a jpg or a png");
    }
}
