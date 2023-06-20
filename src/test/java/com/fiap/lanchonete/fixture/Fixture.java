package com.fiap.lanchonete.fixture;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.fiap.lanchonete.core.domain.CPF;
import com.fiap.lanchonete.core.domain.Cliente;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.ClienteDTO;
import com.fiap.lanchonete.core.domain.dto.ProdutoDTO;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import com.fiap.lanchonete.core.domain.type.StatusPedido;

public class Fixture {
    public class ProdutoFixture {

        public static Produto createProduto() {
            return Produto.builder().id(UUID.randomUUID()).nome("Produto Teste").descricao("Descrição do Produto Teste")
                    .preco(new BigDecimal("9.99")).categoria(CategoriaProduto.BEBIDA).build();
        }
    }

    public class ProdutoDTOFixture {

        public static ProdutoDTO createProdutoDTO() {
            return ProdutoDTO.builder()
                    .nome("Produto Teste")
                    .descricao("Descrição do Produto Teste")
                    .preco(new BigDecimal(9.99))
                    .categoria(CategoriaProduto.BEBIDA)
                    .build();
        }
    }

    public class ClienteFixture {
        public static Cliente criarClientePadrao() {
            return Cliente.builder().id(UUID.randomUUID()).nome("Richard Dallaqua").cpf(new CPF("02366792018"))
                    .telefone("1234567890").dataCadastro(LocalDate.of(1990, 1, 1)).build();
        }
    }

    public class ClienteDTOFixture {
        public static ClienteDTO criarClienteDTOPadrao() {
            return ClienteDTO.builder().nome("Richard Dallaqua").cpf("02366792018")
                    .telefone("1234567890").build();
        }
    }

    public class PedidoFixture {

        public static Pedido criarPedido() {
            return Pedido.builder().id(UUID.randomUUID()).produtoList(new ArrayList<>())
                    .valorTotalDaCompra(BigDecimal.ZERO).quantidadeTotalDeItems(0)
                    .cliente(ClienteFixture.criarClientePadrao()).statusPedido(StatusPedido.ABERTO).build();
        }
    }
}
