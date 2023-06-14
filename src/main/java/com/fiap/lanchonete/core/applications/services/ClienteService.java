package com.fiap.lanchonete.core.applications.services;

import com.fiap.lanchonete.core.applications.ports.ClienteRepository;
import com.fiap.lanchonete.core.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(UUID id) throws Exception {
        return clienteRepository.findById(id).orElseThrow(() -> new Exception("Não foi possivel encontrar o cliente."));
    }
}
