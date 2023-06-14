package com.fiap.lanchonete.core.domain.exception;


public class InvalidTypeException extends RuntimeException{

    public InvalidTypeException(String message){
        super(message);
    }
}
