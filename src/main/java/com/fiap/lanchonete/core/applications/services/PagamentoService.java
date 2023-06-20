package com.fiap.lanchonete.core.applications.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.adapter.driven.PagamentoClient;
import com.fiap.lanchonete.core.applications.ports.PedidoRepository;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.dto.PagamentoResponse;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.StatusPagamento;
import com.fiap.lanchonete.core.domain.type.StatusPedido;

@Service
public class PagamentoService {

    @Autowired
    public PedidoRepository pedidoRepository;

    @Autowired
    public PagamentoClient pagamentoClient;

    public void realizarPagamento(PagamentoService pagamentoService, UUID idPedido) {
        Pedido pedido = pagamentoService.pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        PagamentoResponse response = pagamentoClient.realizarPagamento(idPedido);

        pedido.setStatusPagamento(response.getStatus());
        if (response.getStatus().equals(StatusPagamento.PAGAMENTO_APROVADO)) {
            pedido.setStatusPedido(StatusPedido.RECEBIDO);
        }
        pagamentoService.pedidoRepository.save(pedido);
    }
}
