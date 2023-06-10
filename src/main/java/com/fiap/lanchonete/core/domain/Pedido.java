package com.fiap.lanchonete.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "pedidos")
public class Pedido {

    @Id
    private UUID id;
    private List<Produto> produtoList;
    private BigDecimal valorTotalDaCompra;
    private int quantidadeTotalDeItems;
    private Cliente cliente;
    private StatusPedido statusPedido;
}
