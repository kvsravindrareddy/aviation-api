package com.ravindra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomDataException.class)
    public ResponseEntity<ErrorDetails> handleCustomDataException(CustomDataException customDataException)
    {
        ErrorDetails errorDetails = new ErrorDetails(customDataException.getMsg());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}