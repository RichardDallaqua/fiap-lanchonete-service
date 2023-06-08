package com.fiap.lanchonete.fixture;

import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.ProdutoDTO;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;

import java.math.BigDecimal;
import java.util.UUID;

public class Fixture {
    public class ProdutoFixture {

        public static Produto createProduto() {
            return Produto.builder()
                    .id(UUID.randomUUID())
                    .nome("Produto Teste")
                    .descricao("Descrição do Produto Teste")
                    .preco(new BigDecimal("9.99"))
                    .categoria(CategoriaProduto.BEBIDA).build();
        }
    }

    public class ProdutoDTOFixture {

        public static ProdutoDTO createProdutoDTO() {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Produto Teste");
            produtoDTO.setDescricao("Descrição do Produto Teste");
            produtoDTO.setPreco(new BigDecimal(9.99));
            produtoDTO.setCategoria(CategoriaProduto.BEBIDA.toString());
            return produtoDTO;
        }
    }
}
