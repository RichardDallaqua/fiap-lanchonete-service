package com.fiap.lanchonete.core.domain.exception;

public class PaymentNotApprovedException extends RuntimeException {

    public PaymentNotApprovedException(String message) {
        super(message);
    }
}
