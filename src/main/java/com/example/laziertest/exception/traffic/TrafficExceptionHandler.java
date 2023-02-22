package com.example.laziertest.exception.traffic;

import com.example.laziertest.controller.TrafficController;
import com.example.laziertest.exception.ErrorMessage;
import com.example.laziertest.exception.UserAlreadyExistException;
import com.example.laziertest.exception.UserNotFoundException;
import com.example.laziertest.exception.WrongAddressFormException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = TrafficController.class)
public class TrafficExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistException(UserAlreadyExistException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(WrongAddressFormException.class)
    public ResponseEntity<ErrorMessage> wrongAddressFormException(WrongAddressFormException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
