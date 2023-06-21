package com.fiap.lanchonete.adapter.driver;

import com.fiap.lanchonete.core.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fiap.lanchonete.core.domain.dto.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException bindException) {
        StringBuilder sb = new StringBuilder();
        bindException.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("; "));
        return ErrorResponse.builder().message(sb.toString()).build();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException) {
        return ErrorResponse.builder().message(notFoundException.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidTypeException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleInvalidTypeException(InvalidTypeException invalidTypeException) {
        return ErrorResponse.builder().message(invalidTypeException.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException) {
        return ErrorResponse.builder().message(httpMessageNotReadableException.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(PaymentNotApprovedException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponse handlePaymentNotApprovedException(PaymentNotApprovedException paymentNotApproved) {
        return ErrorResponse.builder().message(paymentNotApproved.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(StatusNotAllowedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleStatusNotAllowedException(StatusNotAllowedException statusNotAllowedException) {
        return ErrorResponse.builder().message(statusNotAllowedException.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(EmptyOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmptyOrderException(EmptyOrderException emptyOrderException) {
        return ErrorResponse.builder().message(emptyOrderException.getMessage()).build();
    }
}
