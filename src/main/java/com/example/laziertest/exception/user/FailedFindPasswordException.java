package com.example.laziertest.exception.user;

public class FailedFindPasswordException extends RuntimeException {
	public FailedFindPasswordException(String message) {
		super(message);
	}
}
