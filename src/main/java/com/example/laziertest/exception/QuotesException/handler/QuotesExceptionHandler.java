package com.example.laziertest.exception.QuotesException.handler;

import com.example.laziertest.controller.QuotesController;
import com.example.laziertest.exception.QuotesException.ErrorMessage;
import com.example.laziertest.exception.QuotesException.WrongIdNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = QuotesController.class)
public class QuotesExceptionHandler {

    @ExceptionHandler(WrongIdNumberException.class)
    public ResponseEntity<ErrorMessage> wrongIdNumberException(WrongIdNumberException exception) {
        return ResponseEntity.internalServerError()
            .body(ErrorMessage.of(exception, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
