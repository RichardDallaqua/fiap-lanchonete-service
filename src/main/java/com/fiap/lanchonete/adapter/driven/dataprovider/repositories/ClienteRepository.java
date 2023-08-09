package com.fiap.lanchonete.adapter.driven.dataprovider.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fiap.lanchonete.core.domain.CPF;
import com.fiap.lanchonete.core.domain.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, UUID> {

    Optional<Cliente> findByCpf(CPF cpf);
}
