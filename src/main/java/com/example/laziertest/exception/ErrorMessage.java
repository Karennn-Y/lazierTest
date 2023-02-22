package com.example.laziertest.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private int code;
    private String errorName;
    private String message;
    private LocalDateTime occurrenceTime;

    public ErrorMessage(Exception exception, HttpStatus status) {
        this.code = status.value();
        this.errorName = exception.getClass().getSimpleName();
        this.message = exception.getLocalizedMessage();
        this.occurrenceTime = LocalDateTime.now();
    }

    public static ErrorMessage of(Exception exception, HttpStatus status) {
        return new ErrorMessage(exception, status);
    }
}
