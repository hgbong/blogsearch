package com.hgbong.blogsearch.exception;

import jdk.jshell.spi.ExecutionControlProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // todo  예외 항목 구체화

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ExceptionResponse response = new ExceptionResponse(httpStatus, e.getMessage());

        return new ResponseEntity(response, httpStatus);
    }

    @ExceptionHandler(value = WebClientResponseException.BadRequest.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(Exception e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(httpStatus, e.getMessage());

        return new ResponseEntity(response, httpStatus);
    }

}
