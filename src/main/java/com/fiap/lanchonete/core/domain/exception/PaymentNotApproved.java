package com.fiap.lanchonete.core.domain.exception;

public class PaymentNotApproved extends RuntimeException {

    public PaymentNotApproved(String message) {
        super(message);
    }
}
