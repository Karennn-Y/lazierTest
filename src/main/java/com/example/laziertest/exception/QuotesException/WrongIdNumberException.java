package com.example.laziertest.exception.QuotesException;

public class WrongIdNumberException extends RuntimeException {

    public WrongIdNumberException(String message) {
        super(message);
    }
}
