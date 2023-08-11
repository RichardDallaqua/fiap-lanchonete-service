package com.fiap.lanchonete.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.commons.exception.NotFoundException;
import com.fiap.lanchonete.commons.type.CategoriaProduto;
import com.fiap.lanchonete.controller.dto.ProdutoDTO;
import com.fiap.lanchonete.dataprovider.database.ProdutoRepository;
import com.fiap.lanchonete.domain.ProdutoDomain;

@Service
public class ProdutoService {

        @Autowired
        private ProdutoRepository produtoRepository;

        public ProdutoDomain cadastrarProduto(ProdutoDTO produtoDTO) {
                return produtoRepository.save(
                                ProdutoDomain.builder().id(UUID.randomUUID()).nome(produtoDTO.getNome())
                                                .descricao(produtoDTO.getDescricao())
                                                .preco(produtoDTO.getPreco()).categoria(produtoDTO.getCategoria())
                                                .build());
        }

        public List<ProdutoDomain> buscarProdutosCategoria(String categoria) {
                CategoriaProduto.existsInValues(categoria);
                return produtoRepository.findAllByCategoria(CategoriaProduto.valueOf(categoria.toUpperCase()));
        }

        public ProdutoDomain atualizarProduto(UUID id, ProdutoDTO produtoDTO) {
                ProdutoDomain produtoToUpdate = produtoRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Não foi possível localizar produto"));
                ProdutoDomain produtoAtualizado = new ProdutoDomain();
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
                ProdutoDomain produtoToDelete = produtoRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Não foi possível localizar produto"));
                produtoRepository.delete(produtoToDelete);
        }
}
