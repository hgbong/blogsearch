package com.hgbong.blogsearch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {

    private final HttpStatus status;
    private final String message;

    private LocalDateTime timestamp;

    public ExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
