package com.fiap.lanchonete.core.domain.exception;

public class EmptyOrderException extends RuntimeException{
    public EmptyOrderException(String message){
        super(message);
    }
}
