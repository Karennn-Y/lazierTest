package com.example.laziertest.exception.todo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private int code;
    private String errorSimpleName;
    private String msg;
    private LocalDateTime timestamp;

    public ErrorMessage(Exception exception, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.errorSimpleName = exception.getClass().getSimpleName();
        this.msg = exception.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorMessage of(Exception exception, HttpStatus httpStatus) {
        return new ErrorMessage(exception, httpStatus);
    }
}
