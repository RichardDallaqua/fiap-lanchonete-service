package com.fiap.lanchonete.core.applications.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import com.fiap.lanchonete.adapter.driven.PagamentoClient;
import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.PedidoRepository;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.PagamentoResponse;
import com.fiap.lanchonete.core.domain.exception.EmptyOrderException;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.StatusPagamento;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import com.fiap.lanchonete.fixture.Fixture;

@MockitoSettings
public class PagamentoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PagamentoClient pagamentoClient;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    void testPedidoNaoEncontrado() {
        Mockito.when(pedidoRepository.findByIdAndStatusPedido(any(), any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pagamentoService.realizarPagamento(UUID.randomUUID()));
    }

    @Test
    void testPedidoVazio() {
        Pedido pedidoSalvo = Fixture.PedidoFixture.criarPedido();
        pedidoSalvo.setId(UUID.randomUUID());
        pedidoSalvo.setStatusPedido(StatusPedido.ABERTO);

        Mockito.when(pedidoRepository.findByIdAndStatusPedido(any(), any())).thenReturn(Optional.of(pedidoSalvo));

        assertThrows(EmptyOrderException.class, () -> {
            pagamentoService.realizarPagamento(UUID.randomUUID());
        });
    }

    @Test
    void testPagamentoRealizado() {
        Pedido pedidoSalvo = Fixture.PedidoFixture.criarPedido();
        pedidoSalvo.setId(UUID.randomUUID());
        pedidoSalvo.setStatusPedido(StatusPedido.ABERTO);
        pedidoSalvo.setProdutoList(Collections.singletonList(Produto.builder().build()));

        Mockito.when(pedidoRepository.findByIdAndStatusPedido(any(), any())).thenReturn(Optional.of(pedidoSalvo));
        PagamentoResponse clientResponse = new PagamentoResponse();
        clientResponse.setStatus(StatusPagamento.PAGAMENTO_APROVADO);
        Mockito.when(pagamentoClient.realizarPagamento(any())).thenReturn(clientResponse);

        PagamentoResponse response = pagamentoService.realizarPagamento(pedidoSalvo.getId());
        assertEquals(StatusPagamento.PAGAMENTO_APROVADO, response.getStatus());
    }
}
