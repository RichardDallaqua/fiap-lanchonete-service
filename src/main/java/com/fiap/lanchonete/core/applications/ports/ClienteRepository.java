package com.fiap.lanchonete.core.applications.ports;

import com.fiap.lanchonete.core.domain.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, UUID> {}
