package com.fiap.lanchonete.core.applications.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.ClienteRepository;
import com.fiap.lanchonete.core.domain.Cliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(UUID id) throws Exception {
        return clienteRepository.findById(id).orElseThrow(() -> new Exception("Não foi possivel encontrar o cliente."));
    }
}
