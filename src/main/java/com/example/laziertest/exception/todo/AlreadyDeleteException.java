package com.example.laziertest.exception.todo;

public class AlreadyDeleteException extends RuntimeException{
	public AlreadyDeleteException(String message) {
		super(message);
	}

}
