package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorController {
    public static final String START_DATE = "startDate";
    public static final String SALARY = "salary";

   @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        Response response;
        String message = exception.getLocalizedMessage();

        if (message.contains(START_DATE)) {
            response = new Response(Response.INVALID_DATE);
        } else if (message.contains(SALARY)) {
            response = new Response(Response.INVALID_SALARY);
        } else {
            response = new Response(message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Response.INVALID_PARAMS));
    }
}
