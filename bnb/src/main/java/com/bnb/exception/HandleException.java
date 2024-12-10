package com.bnb.exception;


import com.bnb.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException e , WebRequest request
    ){
        return new ResponseEntity<>(
                new ErrorDetails(new Date(),e.getMessage(),request.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(
            Exception e , WebRequest request
    ){
        return new ResponseEntity<>(new ErrorDetails(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
            ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
