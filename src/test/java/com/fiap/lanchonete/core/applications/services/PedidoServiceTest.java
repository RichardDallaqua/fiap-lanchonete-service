package com.fiap.lanchonete.core.applications.services;

import com.fiap.lanchonete.core.applications.ports.ClienteRepository;
import com.fiap.lanchonete.core.applications.ports.PedidoRepository;
import com.fiap.lanchonete.core.applications.ports.ProdutoRepository;
import com.fiap.lanchonete.core.domain.Cliente;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import com.fiap.lanchonete.fixture.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@MockitoSettings
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    public void testIniciarPedido() {
        // Criando dados de entrada para o teste
        String cpf = "06452817000";
        Cliente cliente = Fixture.ClienteFixture.criarClientePadrao();

        Pedido pedidoSalvo = Fixture.PedidoFixture.criarPedido();
        pedidoSalvo.setId(UUID.randomUUID());
        pedidoSalvo.setStatusPedido(StatusPedido.ABERTO);

        // Configurando o comportamento dos mocks
        Mockito.when(clienteRepository.findByCpf(Mockito.any())).thenReturn(Optional.of(cliente));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedidoSalvo);

        // Executando o método a ser testado
        Pedido pedido = pedidoService.iniciarPedido(cpf);

        // Verificando o resultado
        Assertions.assertNotNull(pedido);
        Assertions.assertEquals(pedidoSalvo.getId(), pedido.getId());
        Assertions.assertEquals(StatusPedido.ABERTO, pedido.getStatusPedido());

        Mockito.verify(clienteRepository, Mockito.times(1)).findByCpf(Mockito.any());
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testAdicionarProdutosPedido() {
        // Criando dados de entrada para o teste
        UUID idPedido = UUID.randomUUID();
        UUID idProduto = UUID.randomUUID();

        Pedido pedido = Fixture.PedidoFixture.criarPedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(StatusPedido.ABERTO);

        Produto produto = new Produto();
        produto.setId(idProduto);
        produto.setPreco(BigDecimal.valueOf(10.0));

        Pedido pedidoSalvo = Fixture.PedidoFixture.criarPedido();
        pedidoSalvo.setId(idPedido);
        pedidoSalvo.setStatusPedido(StatusPedido.ABERTO);
        pedidoSalvo.getProdutoList().add(produto);
        pedidoSalvo.setQuantidadeTotalDeItems(1);
        pedidoSalvo.setValorTotalDaCompra(BigDecimal.valueOf(10.0));

        // Configurando o comportamento dos mocks
        Mockito.when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        Mockito.when(produtoRepository.findById(idProduto)).thenReturn(Optional.of(produto));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedidoSalvo);

        // Executando o método a ser testado
        Pedido pedidoAtualizado = pedidoService.adicionarProdutosPedido(idPedido, idProduto);

        // Verificando o resultado
        Assertions.assertNotNull(pedidoAtualizado);
        Assertions.assertEquals(pedidoSalvo.getId(), pedidoAtualizado.getId());
        Assertions.assertEquals(StatusPedido.ABERTO, pedidoAtualizado.getStatusPedido());
        Assertions.assertEquals(1, pedidoAtualizado.getProdutoList().size());
        Assertions.assertEquals(1, pedidoAtualizado.getQuantidadeTotalDeItems());
        Assertions.assertEquals(BigDecimal.valueOf(10.0), pedidoAtualizado.getValorTotalDaCompra());

        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(Mockito.eq(idPedido));
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(Mockito.eq(idProduto));
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(Mockito.any());
    }
}