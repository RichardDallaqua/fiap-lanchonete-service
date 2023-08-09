package com.fiap.lanchonete.core.applications.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.ProdutoRepository;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.ProdutoDTO;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;

@Service
public class ProdutoService {

        @Autowired
        private ProdutoRepository produtoRepository;

        public Produto cadastrarProduto(ProdutoDTO produtoDTO) {
                return produtoRepository.save(
                                Produto.builder().id(UUID.randomUUID()).nome(produtoDTO.getNome())
                                                .descricao(produtoDTO.getDescricao())
                                                .preco(produtoDTO.getPreco()).categoria(produtoDTO.getCategoria())
                                                .build());
        }

        public List<Produto> buscarProdutosCategoria(String categoria) {
                CategoriaProduto.existsInValues(categoria);
                return produtoRepository.findAllByCategoria(CategoriaProduto.valueOf(categoria.toUpperCase()));
        }

        public Produto atualizarProduto(UUID id, ProdutoDTO produtoDTO) {
                Produto produtoToUpdate = produtoRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Não foi possível localizar produto"));
                Produto produtoAtualizado = new Produto();
                produtoAtualizado.setId(produtoToUpdate.getId());
                produtoAtualizado.setNome(
                                Boolean.TRUE.equals(Objects.nonNull(produtoDTO.getNome())) ? produtoDTO.getNome()
                                                : produtoToUpdate.getNome());
                produtoAtualizado.setDescricao(Boolean.TRUE.equals(Objects.nonNull(produtoDTO.getDescricao()))
                                ? produtoDTO.getDescricao()
                                : produtoToUpdate.getDescricao());
                produtoAtualizado.setPreco(
                                Boolean.TRUE.equals(Objects.nonNull(produtoDTO.getPreco())) ? produtoDTO.getPreco()
                                                : produtoToUpdate.getPreco());
                produtoAtualizado.setCategoria(Boolean.TRUE.equals(Objects.nonNull(produtoDTO.getCategoria()))
                                ? produtoDTO.getCategoria()
                                : produtoToUpdate.getCategoria());
                if (Objects.equals(produtoToUpdate, produtoAtualizado)) {
                        return produtoToUpdate;
                } else {
                        return produtoRepository.save(produtoAtualizado);
                }
        }

        public void deletarProduto(UUID id) {
                Produto produtoToDelete = produtoRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Não foi possível localizar produto"));
                produtoRepository.delete(produtoToDelete);
        }
}
