package com.example.laziertest.exception.todo;

public class FailedWriteException extends RuntimeException{
	public FailedWriteException(String message) {
		super(message);
	}
}
