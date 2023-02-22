package com.example.laziertest.exception;

public class WrongAddressFormException extends RuntimeException{

    public WrongAddressFormException(String message) {
        super(message);
    }
}
