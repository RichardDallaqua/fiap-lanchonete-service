package com.fiap.lanchonete.controller.dto;

import com.fiap.lanchonete.commons.type.StatusPagamento;

import lombok.Data;

@Data
public class PagamentoResponseDTO {
    private StatusPagamento status;
}
