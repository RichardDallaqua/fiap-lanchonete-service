package com.fiap.lanchonete.adapter.driven.dataprovider.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, UUID> {

    List<Produto> findAllByCategoria(CategoriaProduto categoriaProduto);
}
