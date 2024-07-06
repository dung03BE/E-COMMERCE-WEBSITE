package com.example.shopapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidateExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataException(DataNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errors =new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
        String key = ((FieldError)objectError).getField();
        String message =objectError.getDefaultMessage();
        errors.put(key,message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
