package com.fiap.lanchonete.core.applications.services;

import com.fiap.lanchonete.core.applications.ports.ProdutoRepository;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.ProdutoDTO;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(ProdutoDTO produtoDTO){
        CategoriaProduto.existsInValues(produtoDTO.getCategoria());
        return produtoRepository.save(Produto.builder().id(UUID.randomUUID()).nome(produtoDTO.getNome())
                .descricao(produtoDTO.getDescricao()).preco(produtoDTO.getPreco())
                .categoria(CategoriaProduto.valueOf(produtoDTO.getCategoria().toUpperCase())).build());
    }

    public List<Produto> buscarProdutosCategoria(String categoria){
        CategoriaProduto.existsInValues(categoria);
        return produtoRepository.findAllByCategoria(CategoriaProduto.valueOf(categoria.toUpperCase()));
    }

    public Produto atualizarProduto(UUID id, ProdutoDTO produtoDTO){
        Produto produtoToUpdate = produtoRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi possível localizar produto"));
        CategoriaProduto.existsInValues(produtoDTO.getCategoria());
        produtoToUpdate.setNome(produtoDTO.getNome());
        produtoToUpdate.setDescricao(produtoDTO.getDescricao());
        produtoToUpdate.setPreco(produtoDTO.getPreco());
        produtoToUpdate.setCategoria(CategoriaProduto.valueOf(produtoDTO.getCategoria().toUpperCase()));
        return produtoRepository.save(produtoToUpdate);
    }

    public void deletarProduto(UUID id){
        Produto produtoToDelete = produtoRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi possível localizar produto"));
        produtoRepository.delete(produtoToDelete);
    }
}
