package com.example.laziertest.exception;

public class NotFoundUserIdException extends RuntimeException {
    public NotFoundUserIdException(String message) {
        super(message);
    }
}