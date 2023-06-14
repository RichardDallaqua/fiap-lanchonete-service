package com.fiap.lanchonete.core.applications.ports;

import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, UUID> {

    List<Produto> findAllByCategoria(CategoriaProduto categoriaProduto);
}
