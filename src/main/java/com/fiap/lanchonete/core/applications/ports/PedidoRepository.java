package com.fiap.lanchonete.core.applications.ports;

import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, UUID>{

    @Query("{ 'statusPedido' : { $ne: ?0 } }")
    List<Pedido> findAllExcept(List<StatusPedido> statusPedidos);

    Optional<Pedido> findByIdAndStatusPedido(UUID id, StatusPedido statusPedido);
}
