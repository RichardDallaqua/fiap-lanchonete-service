package com.fiap.lanchonete.core.domain.type;

import com.fiap.lanchonete.core.domain.exception.InvalidTypeException;

import java.util.Arrays;

public enum CategoriaProduto {
    ACOMPANHAMENTO,
    BEBIDA,
    LANCHE;

    public static void existsInValues(String categoria){
       if(Arrays.stream(values()).noneMatch(x -> x.name().equalsIgnoreCase(categoria))){
           throw new InvalidTypeException(String.format("Categoria de produto %s é inválida", categoria));
       }
    }
}
