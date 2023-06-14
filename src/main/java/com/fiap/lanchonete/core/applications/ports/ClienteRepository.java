package com.fiap.lanchonete.core.applications.ports;

import com.fiap.lanchonete.core.domain.CPF;
import com.fiap.lanchonete.core.domain.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, UUID> {

    Optional<Cliente> findByCpf(CPF cpf);
}
