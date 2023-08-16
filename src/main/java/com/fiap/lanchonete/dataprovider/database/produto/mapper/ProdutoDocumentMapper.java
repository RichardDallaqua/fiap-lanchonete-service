package com.fiap.lanchonete.dataprovider.database.produto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fiap.lanchonete.dataprovider.database.pedido.documents.PedidoDocument;
import com.fiap.lanchonete.dataprovider.database.produto.document.ProdutoDocument;
import com.fiap.lanchonete.domain.ProdutoDomain;

@Mapper(componentModel = "spring")
public interface ProdutoDocumentMapper {

    ProdutoDomain toDomain(ProdutoDocument document);

    List<ProdutoDomain> toDomainList(List<ProdutoDocument> document);

    ProdutoDocument toDocument(ProdutoDomain domain);

}
