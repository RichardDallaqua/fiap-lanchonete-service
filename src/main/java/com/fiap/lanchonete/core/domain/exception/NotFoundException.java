package com.fiap.lanchonete.core.domain.exception;

import org.springframework.data.mongodb.core.aggregation.BooleanOperators;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
