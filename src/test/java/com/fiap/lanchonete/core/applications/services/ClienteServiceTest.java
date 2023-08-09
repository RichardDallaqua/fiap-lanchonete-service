package com.fiap.lanchonete.core.applications.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.ClienteRepository;
import com.fiap.lanchonete.core.domain.Cliente;
import com.fiap.lanchonete.fixture.Fixture;

@MockitoSettings
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testCadastrarCliente() {
        Cliente cliente = Fixture.ClienteFixture.criarClientePadrao();

        clienteService.cadastrarCliente(cliente);

        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void testBuscarClientePorIdExistente() throws Exception {
        Cliente cliente = Fixture.ClienteFixture.criarClientePadrao();

        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.buscarClientePorId(cliente.getId());

        Assertions.assertEquals(cliente, result);
    }

    @Test
    void testBuscarClientePorIdNaoExistente() {
        UUID clienteId = UUID.randomUUID();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> clienteService.buscarClientePorId(clienteId));
    }
}