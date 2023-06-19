package com.fiap.lanchonete.core.applications.services;

import com.fiap.lanchonete.core.applications.ports.ClienteRepository;
import com.fiap.lanchonete.core.domain.CPF;
import com.fiap.lanchonete.core.domain.Cliente;
import com.fiap.lanchonete.fixture.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        verify(clienteRepository, times(1)).save(cliente);
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