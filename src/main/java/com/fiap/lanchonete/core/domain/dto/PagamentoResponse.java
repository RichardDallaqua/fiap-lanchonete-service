package com.fiap.lanchonete.core.domain.dto;

import com.fiap.lanchonete.core.domain.type.StatusPagamento;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import lombok.Data;

@Data
public class PagamentoResponse {

    private StatusPagamento status;
}
