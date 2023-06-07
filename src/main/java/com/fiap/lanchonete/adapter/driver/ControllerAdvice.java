package com.fiap.lanchonete.adapter.driver;

import com.fiap.lanchonete.core.domain.dto.ErrorResponse;
import com.fiap.lanchonete.core.domain.exception.InvalidTypeException;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException bindException){
        StringBuilder sb = new StringBuilder();
        bindException.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("; "));
        return ErrorResponse.builder().message(sb.toString()).build();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException){
        return ErrorResponse.builder().message(notFoundException.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidTypeException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleInvalidTypeException(InvalidTypeException invalidTypeException){
        return ErrorResponse.builder().message(invalidTypeException.getMessage()).build();
    }
}
