package com.fiap.lanchonete.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.commons.exception.NotFoundException;
import com.fiap.lanchonete.commons.exception.PaymentNotApprovedException;
import com.fiap.lanchonete.commons.type.StatusPagamento;
import com.fiap.lanchonete.commons.type.StatusPedido;
import com.fiap.lanchonete.dataprovider.database.ProdutoRepository;
import com.fiap.lanchonete.dataprovider.database.cliente.repository.ClienteRepository;
import com.fiap.lanchonete.dataprovider.database.pedido.repository.PedidoRepository;
import com.fiap.lanchonete.domain.CPFDomain;
import com.fiap.lanchonete.domain.ClienteDomain;
import com.fiap.lanchonete.domain.PedidoDomain;
import com.fiap.lanchonete.domain.ProdutoDomain;
import com.fiap.lanchonete.services.gateways.PedidoGateway;

@Service
public class PedidoService {

    @Autowired
    private PedidoGateway pedidoGateway;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoDomain iniciarPedido(String cpf) {
        PedidoDomain pedido = PedidoDomain.builder().id(UUID.randomUUID()).produtoList(new ArrayList<>()).quantidadeTotalDeItems(0)
                .valorTotalDaCompra(BigDecimal.ZERO).statusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO).build();
        if (Objects.isNull(cpf) || cpf.isBlank()) {
            pedido.setCliente(null);
        } else {
            ClienteDomain cliente = clienteRepository.findByCpf(new CPFDomain(cpf))
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            pedido.setCliente(cliente);
        }
        pedido.setStatusPedido(StatusPedido.ABERTO);
        pedidoGateway.save(pedido);
        return pedido;
    }

    public PedidoDomain adicionarProdutosPedido(UUID idPedido, UUID idProduto) {
        PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO);
        ProdutoDomain produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        pedido.getProdutoList().add(produto);
        pedido.setQuantidadeTotalDeItems(pedido.getProdutoList().size());
        pedido.setValorTotalDaCompra(pedido.getValorTotalDaCompra().add(produto.getPreco()));
        pedidoGateway.save(pedido);
        return pedido;
    }

    public PedidoDomain removerProdutosPedido(UUID idPedido, UUID idProduto) {
        PedidoDomain pedido = pedidoGateway.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO);
        ProdutoDomain produtoToRemove = pedido.getProdutoList().stream().filter(x -> x.getId().equals(idProduto)).findFirst()
                .orElseThrow(() -> new NotFoundException("Produto não encontrado no pedido"));
        pedido.getProdutoList().remove(produtoToRemove);
        pedido.setQuantidadeTotalDeItems(pedido.getProdutoList().size());
        pedido.setValorTotalDaCompra(pedido.getValorTotalDaCompra().subtract(produtoToRemove.getPreco()));
        pedidoGateway.save(pedido);
        
        return pedido;
    }

    public List<PedidoDomain> listarPedidosNaoFinalizados() {
        return pedidoGateway.findAllExcept(Arrays.asList(StatusPedido.PEDIDO_RETIRADO, StatusPedido.CANCELADO));
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
