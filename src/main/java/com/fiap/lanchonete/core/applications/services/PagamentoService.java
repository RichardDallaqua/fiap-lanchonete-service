package com.fiap.lanchonete.core.applications.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.lanchonete.core.applications.ports.PedidoRepository;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.dto.PagamentoResponse;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.StatusPagamento;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void realizarPagamento(UUID idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        WebClient webClient = WebClient.create();
        webClient.get().uri("https://007z3.mocklab.io/realizarpagamento").accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(PagamentoResponse.class).subscribe(respostaPagamento -> {
                    pedido.setStatusPagamento(respostaPagamento.getStatus());
                    if (respostaPagamento.getStatus().equals(StatusPagamento.PAGAMENTO_APROVADO)) {
                        pedido.setStatusPedido(StatusPedido.RECEBIDO);
                    }
                    pedidoRepository.save(pedido);
                });
    }
}
