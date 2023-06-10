package com.fiap.lanchonete.core.applications.ports;

import com.fiap.lanchonete.core.domain.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, UUID> {
}
