package com.hgbong.blogsearch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.stream.Collectors;

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

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(httpStatus, "the input values are not valid.");
        if (!CollectionUtils.isEmpty(e.getFieldErrors())) {
            response.setFields(e.getFieldErrors().stream().map(fe -> {
                ExceptionResponse.Field field = new ExceptionResponse.Field();
                field.setFieldName(fe.getField());
                field.setMessage(fe.getDefaultMessage());
                return field;
            }).collect(Collectors.toList()));
        }

        return new ResponseEntity(response, httpStatus);
    }

    @ExceptionHandler(value = WebClientResponseException.BadRequest.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(WebClientResponseException.BadRequest e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(httpStatus, e.getMessage());

        return new ResponseEntity(response, httpStatus);
    }

}
