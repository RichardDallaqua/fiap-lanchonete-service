package com.fiap.lanchonete.core.domain.type;

import com.fiap.lanchonete.core.domain.exception.InvalidTypeException;

import java.util.Arrays;

public enum StatusPedido {
    ABERTO,
    CANCELADO,
    RECEBIDO,
    PREPARANDO_PEDIDO,
    RETIRAR_PEDIDO,
    PEDIDO_RETIRADO;

    public static void existsInValues(String statusPedido){
        if(Arrays.stream(values()).noneMatch(x -> x.name().equalsIgnoreCase(statusPedido))){
            throw new InvalidTypeException(String.format("O status do pedido %s é inválido", statusPedido));
        }
    }
}
