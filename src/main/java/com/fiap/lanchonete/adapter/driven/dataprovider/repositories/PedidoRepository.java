package com.fiap.lanchonete.adapter.driven.dataprovider.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.type.StatusPedido;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, UUID> {

    @Query("{ 'statusPedido' : { $ne: ?0 } }")
    List<Pedido> findAllExcept(List<StatusPedido> statusPedidos);

    Optional<Pedido> findByIdAndStatusPedido(UUID id, StatusPedido statusPedido);
}
