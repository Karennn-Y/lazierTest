package com.example.laziertest.exception.user;

public class UnauthorizedRefreshTokenException extends RuntimeException {
    public UnauthorizedRefreshTokenException(String message) {
        super(message);
    }
}
