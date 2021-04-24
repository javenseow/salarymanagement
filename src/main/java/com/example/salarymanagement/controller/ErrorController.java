package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        Response response;

        if (exception.getLocalizedMessage().contains("id")) {
            response = new Response(Response.INVALID_ID);
        } else if (exception.getLocalizedMessage().contains("login")) {
            response = new Response(Response.INVALID_LOGIN);
        } else if (exception.getLocalizedMessage().contains("name")) {
            response = new Response(Response.INVALID_NAME);
        } else if (exception.getLocalizedMessage().contains("salary")) {
            response = new Response(Response.INVALID_SALARY);
        } else if (exception.getLocalizedMessage().contains("date")) {
            response = new Response(Response.INVALID_DATE);
        } else {
            response = new Response(exception.getLocalizedMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
