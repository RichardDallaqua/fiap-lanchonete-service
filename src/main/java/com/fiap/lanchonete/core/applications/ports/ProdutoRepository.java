package com.fiap.lanchonete.core.applications.ports;

import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProdutoRepository extends MongoRepository<Produto, String> {

    List<Produto> findAllByCategoria(CategoriaProduto categoriaProduto);
}
