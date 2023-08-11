package com.fiap.lanchonete.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.domain.ClienteDomain;
import com.fiap.lanchonete.services.gateways.ClienteGateway;

@Service
public class ClienteService {

		@Autowired
		private ClienteGateway clienteGateway;
	
    public ClienteDomain cadastrarCliente(ClienteDomain cliente) {
        clienteGateway.save(cliente);
        return cliente;
    }

    public ClienteDomain buscarClientePorId(UUID id) throws Exception {
        return clienteGateway.findById(id);
    }
}
