package com.fiap.lanchonete.core.applications.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.core.applications.ports.ClienteRepository;
import com.fiap.lanchonete.core.domain.CPF;
import com.fiap.lanchonete.core.domain.Cliente;
import com.fiap.lanchonete.core.domain.dto.ClienteDTO;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
        return clienteRepository.save(
                Cliente.builder().id(UUID.randomUUID())
                        .nome(clienteDTO.getNome())
                        .cpf(new CPF(clienteDTO.getCpf()))
                        .telefone(clienteDTO.getCpf())
                        .dataCadastro(LocalDate.now())
                        .build());
    }

    public Cliente buscarClientePorId(UUID id) throws Exception {
        return clienteRepository.findById(id).orElseThrow(() -> new Exception("Não foi possivel encontrar o cliente."));
    }
}
