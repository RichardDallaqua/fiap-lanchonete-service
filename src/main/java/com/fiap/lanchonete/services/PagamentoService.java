package com.fiap.lanchonete.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.commons.exception.EmptyOrderException;
import com.fiap.lanchonete.commons.type.StatusPagamento;
import com.fiap.lanchonete.commons.type.StatusPedido;
import com.fiap.lanchonete.controller.dto.PagamentoResponseDTO;
import com.fiap.lanchonete.dataprovider.pagamento.PagamentoClient;
import com.fiap.lanchonete.domain.PedidoDomain;
import com.fiap.lanchonete.services.gateways.PedidoGateway;

@Service
public class PagamentoService {

    @Autowired
    private PedidoGateway pedidoGateway;

    @Autowired
    private PagamentoClient pagamentoClient;

    public PagamentoResponseDTO realizarPagamento(UUID idPedido) {
        PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO);

        if (pedido.getProdutoList().isEmpty()) {
            throw new EmptyOrderException("O pedido não pode estar vazio para realizar pagamento");
        }

        PagamentoResponseDTO response = pagamentoClient.realizarPagamento(idPedido);

        // TODO: implementar lógica para verificar pagamento recusado
        pedido.setStatusPagamento(response.getStatus());
        if (response.getStatus().equals(StatusPagamento.PAGAMENTO_APROVADO)) {
            pedido.setStatusPedido(StatusPedido.RECEBIDO);
        }
        pedidoGateway.save(pedido);

        return response;
    }
}
