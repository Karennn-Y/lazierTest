package com.example.laziertest.exception.todo;

import com.example.laziertest.controller.TodoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = TodoController.class)
public class TodoExceptionHandler {

	@ExceptionHandler(FailedWriteException.class)
	public ResponseEntity<ErrorMessage> failedWriteException(FailedWriteException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(AlreadyDeleteException.class)
	public ResponseEntity<ErrorMessage> alreadyDeleteException(AlreadyDeleteException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
