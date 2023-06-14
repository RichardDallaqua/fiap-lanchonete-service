package com.fiap.lanchonete.core.applications.services;

import com.fiap.lanchonete.core.applications.ports.ProdutoRepository;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.ProdutoDTO;
import com.fiap.lanchonete.core.domain.exception.InvalidTypeException;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import com.fiap.lanchonete.fixture.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@MockitoSettings
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarProduto() {
        // Dados de entrada para o teste
        ProdutoDTO produtoDTO = Fixture.ProdutoDTOFixture.createProdutoDTO();

        // Comportamento esperado do mock do repositório
        when(produtoRepository.save(any(Produto.class))).thenReturn(Fixture.ProdutoFixture.createProduto());

        // Chamada do método a ser testado
        Produto result = produtoService.cadastrarProduto(produtoDTO);

        // Verificações
        verify(produtoRepository, times(1)).save(any(Produto.class));
        Assertions.assertNotNull(result);
    }

    @Test
    void testBuscarProdutosCategoria() {
        // Dados de entrada para o teste
        String categoria =CategoriaProduto.BEBIDA.toString();

        // Comportamento esperado do mock do repositório
        List<Produto> produtos = new ArrayList<>();
        when(produtoRepository.findAllByCategoria(any())).thenReturn(produtos);

        // Chamada do método a ser testado
        List<Produto> result = produtoService.buscarProdutosCategoria(categoria);

        // Verificações
        verify(produtoRepository, times(1)).findAllByCategoria(any());
        Assertions.assertNotNull(result);
    }

    @Test
    void testAtualizarProduto() {
        // Dados de entrada para o teste
        UUID id = UUID.randomUUID();
        ProdutoDTO produtoDTO = Fixture.ProdutoDTOFixture.createProdutoDTO();

        // Comportamento esperado do mock do repositório
        Produto produtoMock = Fixture.ProdutoFixture.createProduto();
        when(produtoRepository.findById(any())).thenReturn(Optional.of(produtoMock));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoMock);

        // Chamada do método a ser testado
        Produto result = produtoService.atualizarProduto(id, produtoDTO);

        // Verificações
        verify(produtoRepository, times(1)).findById(any());
        verify(produtoRepository, times(1)).save(any(Produto.class));
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeletarProduto() {
        // Dados de entrada para o teste
        UUID id = UUID.randomUUID();

        // Comportamento esperado do mock do repositório
        Produto produtoMock = Fixture.ProdutoFixture.createProduto();
        when(produtoRepository.findById(any())).thenReturn(Optional.of(produtoMock));

        // Chamada do método a ser testado
        produtoService.deletarProduto(id);

        // Verificações
        verify(produtoRepository, times(1)).findById(any());
        verify(produtoRepository, times(1)).delete(any(Produto.class));
    }

    @Test
    void testDeveRetornarErroSeInformarUmaCategoriaInvalidaAoCadastrarProduto() {
        // Dados de entrada para o teste
        ProdutoDTO produtoDTO = Fixture.ProdutoDTOFixture.createProdutoDTO();
        produtoDTO.setCategoria("CATEGORIA_INVALIDA");

        // Chamada do método a ser testado e validação
        Assertions.assertThrows(InvalidTypeException.class, () -> produtoService.cadastrarProduto(produtoDTO));
    }

    @Test
    void testDeveRetornarErroSeNaoEncontrarUmProdutoParaDeletar() {
        // Dados de entrada para o teste
        UUID id = UUID.randomUUID();

        // Comportamento esperado do mock do repositório
        when(produtoRepository.findById(any())).thenReturn(Optional.empty());

        // Chamada do método a ser testado e validação
        Assertions.assertThrows(NotFoundException.class, () -> produtoService.deletarProduto(id));
    }
}
