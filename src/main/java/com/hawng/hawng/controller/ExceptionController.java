package com.hawng.hawng.controller;

import com.hawng.hawng.exception.InvalidRequest;
import com.hawng.hawng.exception.MainException;
import com.hawng.hawng.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.builder().code("400").message("잘못됨").build();

     
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }



    @ExceptionHandler(MainException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> MainException(MainException e) {
        int statusCode = e.getstatsCod();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation()).build();


        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode).body(body);
        return response;
    }












}
