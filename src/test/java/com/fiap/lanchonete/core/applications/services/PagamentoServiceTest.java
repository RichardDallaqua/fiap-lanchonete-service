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

import com.fiap.lanchonete.commons.exception.EmptyOrderException;
import com.fiap.lanchonete.commons.exception.NotFoundException;
import com.fiap.lanchonete.commons.type.StatusPagamento;
import com.fiap.lanchonete.commons.type.StatusPedido;
import com.fiap.lanchonete.controller.dto.PagamentoResponseDTO;
import com.fiap.lanchonete.dataprovider.database.pedido.repository.PedidoRepository;
import com.fiap.lanchonete.dataprovider.pagamento.PagamentoClient;
import com.fiap.lanchonete.domain.PedidoDomain;
import com.fiap.lanchonete.domain.ProdutoDomain;
import com.fiap.lanchonete.fixture.Fixture;
import com.fiap.lanchonete.services.PagamentoService;

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
        PedidoDomain pedidoSalvo = Fixture.PedidoFixture.criarPedido();
        pedidoSalvo.setId(UUID.randomUUID());
        pedidoSalvo.setStatusPedido(StatusPedido.ABERTO);

        Mockito.when(pedidoRepository.findByIdAndStatusPedido(any(), any())).thenReturn(Optional.of(pedidoSalvo));

        assertThrows(EmptyOrderException.class, () -> {
            pagamentoService.realizarPagamento(UUID.randomUUID());
        });
    }

    @Test
    void testPagamentoRealizado() {
        PedidoDomain pedidoSalvo = Fixture.PedidoFixture.criarPedido();
        pedidoSalvo.setId(UUID.randomUUID());
        pedidoSalvo.setStatusPedido(StatusPedido.ABERTO);
        pedidoSalvo.setProdutoList(Collections.singletonList(ProdutoDomain.builder().build()));

        Mockito.when(pedidoRepository.findByIdAndStatusPedido(any(), any())).thenReturn(Optional.of(pedidoSalvo));
        PagamentoResponseDTO clientResponse = new PagamentoResponseDTO();
        clientResponse.setStatus(StatusPagamento.PAGAMENTO_APROVADO);
        Mockito.when(pagamentoClient.realizarPagamento(any())).thenReturn(clientResponse);

        PagamentoResponseDTO response = pagamentoService.realizarPagamento(pedidoSalvo.getId());
        assertEquals(StatusPagamento.PAGAMENTO_APROVADO, response.getStatus());
    }
}
