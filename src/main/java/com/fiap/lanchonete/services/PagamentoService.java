package com.fiap.lanchonete.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.lanchonete.commons.exception.EmptyOrderException;
import com.fiap.lanchonete.commons.type.StatusPagamento;
import com.fiap.lanchonete.commons.type.StatusPedido;
import com.fiap.lanchonete.controller.dto.PagamentoResponseDTO;
import com.fiap.lanchonete.dataprovider.database.pedido.PedidoDataProvider;
import com.fiap.lanchonete.dataprovider.pagamento.PagamentoClient;
import com.fiap.lanchonete.domain.PedidoDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PagamentoService {

    @Autowired
    private PedidoDataProvider pedidoGateway;

    @Autowired
    private PagamentoClient pagamentoClient;

    @Autowired
    private ObjectMapper objectMapper;

    public void realizarPagamento(UUID idPedido) {
        PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO);

        if (pedido.getProdutoList().isEmpty()) {
            throw new EmptyOrderException("O pedido não pode estar vazio para realizar pagamento");
        }

        pagamentoClient.realizarPagamento(idPedido);
    }

    @RabbitListener(queues = "webhook")
    public void webhookListener(String message) {
        try{
            PagamentoResponseDTO response = objectMapper.readValue(message, PagamentoResponseDTO.class);
            PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(response.getIdPedido(), StatusPedido.ABERTO);
            pedido.setStatusPagamento(response.getStatus());
            if (response.getStatus().equals(StatusPagamento.PAGAMENTO_APROVADO)) {
                pedido.setStatusPedido(StatusPedido.RECEBIDO);
            }
            pedidoGateway.save(pedido);
        }catch (Exception ex){
            log.error("Erro ao consumir a mensagem de pagamento");
        }
    }
}
