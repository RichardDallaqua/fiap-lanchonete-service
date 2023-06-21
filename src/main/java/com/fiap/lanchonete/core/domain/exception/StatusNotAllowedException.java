package com.fiap.lanchonete.core.domain.exception;

public class StatusNotAllowedException extends RuntimeException{
    public StatusNotAllowedException(String message){
        super(message);
    }
}
