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

import com.fiap.lanchonete.dataprovider.database.cliente.repository.ClienteRepository;
import com.fiap.lanchonete.domain.ClienteDomain;
import com.fiap.lanchonete.fixture.Fixture;
import com.fiap.lanchonete.services.ClienteService;

@MockitoSettings
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testCadastrarCliente() {
        ClienteDomain cliente = Fixture.ClienteFixture.criarClientePadrao();

        clienteService.cadastrarCliente(cliente);

        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void testBuscarClientePorIdExistente() throws Exception {
        ClienteDomain cliente = Fixture.ClienteFixture.criarClientePadrao();

        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        ClienteDomain result = clienteService.buscarClientePorId(cliente.getId());

        Assertions.assertEquals(cliente, result);
    }

    @Test
    void testBuscarClientePorIdNaoExistente() {
        UUID clienteId = UUID.randomUUID();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> clienteService.buscarClientePorId(clienteId));
    }
}