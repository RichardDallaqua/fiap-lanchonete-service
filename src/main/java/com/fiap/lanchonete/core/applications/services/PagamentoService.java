package com.fiap.lanchonete.core.applications.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.adapter.driven.PagamentoClient;
import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.PedidoRepository;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.dto.PagamentoResponse;
import com.fiap.lanchonete.core.domain.exception.EmptyOrderException;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.StatusPagamento;
import com.fiap.lanchonete.core.domain.type.StatusPedido;

@Service
public class PagamentoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoClient pagamentoClient;

    public PagamentoResponse realizarPagamento(UUID idPedido) {
        Pedido pedido = pedidoRepository.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO)
                .orElseThrow(
                        () -> new NotFoundException("Pedido não encontrado, ou apresenta status diferente de ABERTO"));

        if (pedido.getProdutoList().isEmpty()) {
            throw new EmptyOrderException("O pedido não pode estar vazio para realizar pagamento");
        }

        PagamentoResponse response = pagamentoClient.realizarPagamento(idPedido);

        // TODO: implementar lógica para verificar pagamento recusado
        pedido.setStatusPagamento(response.getStatus());
        if (response.getStatus().equals(StatusPagamento.PAGAMENTO_APROVADO)) {
            pedido.setStatusPedido(StatusPedido.RECEBIDO);
        }
        pedidoRepository.save(pedido);

        return response;
    }
}
