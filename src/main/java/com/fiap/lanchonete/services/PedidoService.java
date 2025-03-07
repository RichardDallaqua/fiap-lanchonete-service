package com.fiap.lanchonete.services;

import com.fiap.lanchonete.commons.exception.NotFoundException;
import com.fiap.lanchonete.commons.exception.PaymentNotApprovedException;
import com.fiap.lanchonete.commons.type.StatusPagamento;
import com.fiap.lanchonete.commons.type.StatusPedido;
import com.fiap.lanchonete.commons.utils.JwtDecode;
import com.fiap.lanchonete.dataprovider.database.cliente.ClienteDataProvider;
import com.fiap.lanchonete.dataprovider.database.pedido.PedidoDataProvider;
import com.fiap.lanchonete.dataprovider.database.produto.ProdutoDataProvider;
import com.fiap.lanchonete.domain.ClienteDomain;
import com.fiap.lanchonete.domain.PedidoDomain;
import com.fiap.lanchonete.domain.ProdutoDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoDataProvider pedidoGateway;

    @Autowired
    private ClienteDataProvider clienteGateway;

    @Autowired
    private ProdutoDataProvider produtoGateway;

    public PedidoDomain iniciarPedido(String authorization) {
        String cpf = JwtDecode.getCPFFromJWT(authorization);
        PedidoDomain pedido = PedidoDomain.builder().id(UUID.randomUUID()).produtoList(new ArrayList<>())
                .quantidadeTotalDeItems(0).valorTotalDaCompra(BigDecimal.ZERO)
                .statusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO).build();
        if (Objects.isNull(cpf) || cpf.isBlank()) {
            pedido.setCliente(null);
        } else {
            ClienteDomain cliente = clienteGateway.findByCpf(cpf);
            pedido.setCliente(cliente);
        }
        pedido.setStatusPedido(StatusPedido.ABERTO);
        pedidoGateway.save(pedido);
        return pedido;
    }

    public PedidoDomain adicionarProdutosPedido(UUID idPedido, UUID idProduto) {
        PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO);
        ProdutoDomain produto = produtoGateway.findById(idProduto);
        pedido.getProdutoList().add(produto);
        pedido.setQuantidadeTotalDeItems(pedido.getProdutoList().size());
        pedido.setValorTotalDaCompra(pedido.getValorTotalDaCompra().add(produto.getPreco()));
        pedidoGateway.save(pedido);
        return pedido;
    }

    public PedidoDomain removerProdutosPedido(UUID idPedido, UUID idProduto) {
        PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO);
        ProdutoDomain produtoToRemove = pedido.getProdutoList().stream().filter(x -> x.getId().equals(idProduto))
                .findFirst().orElseThrow(() -> new NotFoundException("Produto não encontrado no pedido"));
        pedido.getProdutoList().remove(produtoToRemove);
        pedido.setQuantidadeTotalDeItems(pedido.getProdutoList().size());
        pedido.setValorTotalDaCompra(pedido.getValorTotalDaCompra().subtract(produtoToRemove.getPreco()));
        pedidoGateway.save(pedido);

        return pedido;
    }

    public List<PedidoDomain> listarPedidosNaoFinalizados() {
        return pedidoGateway.findAllExcept(Arrays.asList(StatusPedido.PEDIDO_RETIRADO, StatusPedido.CANCELADO));
    }

    public PedidoDomain listarDadosDoPedido(UUID idPedido) {
        return pedidoGateway.findById(idPedido);
    }

    public StatusPagamento buscarStatusPagamento(UUID idPedido) {
        return pedidoGateway.findById(idPedido).getStatusPagamento();
    }

    public void alterarStatusPedido(UUID id, StatusPedido statusPedido) {
        PedidoDomain pedido = pedidoGateway.findById(id);

        if (!statusPedido.equals(StatusPedido.CANCELADO)
                && pedido.getStatusPagamento().equals(StatusPagamento.AGUARDANDO_PAGAMENTO)) {
            throw new PaymentNotApprovedException("Alteração de status não permitida, pois o pagamento não efetuado");
        }

        StatusPedido.verifyOrderOnUpdate(pedido.getStatusPedido(), statusPedido);

        pedido.setStatusPedido(statusPedido);
        pedidoGateway.save(pedido);
    }

}
