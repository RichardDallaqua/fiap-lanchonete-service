package com.fiap.lanchonete.core.applications.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.ClienteRepository;
import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.PedidoRepository;
import com.fiap.lanchonete.adapter.driven.dataprovider.repositories.ProdutoRepository;
import com.fiap.lanchonete.core.domain.CPF;
import com.fiap.lanchonete.core.domain.Cliente;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.exception.NotFoundException;
import com.fiap.lanchonete.core.domain.exception.PaymentNotApprovedException;
import com.fiap.lanchonete.core.domain.type.StatusPagamento;
import com.fiap.lanchonete.core.domain.type.StatusPedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido iniciarPedido(String cpf) {
        Pedido pedido = Pedido.builder().id(UUID.randomUUID()).produtoList(new ArrayList<>()).quantidadeTotalDeItems(0)
                .valorTotalDaCompra(BigDecimal.ZERO).statusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO).build();
        if (Objects.isNull(cpf) || cpf.isBlank()) {
            pedido.setCliente(null);
        } else {
            Cliente cliente = clienteRepository.findByCpf(new CPF(cpf))
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            pedido.setCliente(cliente);
        }
        pedido.setStatusPedido(StatusPedido.ABERTO);
        return pedidoRepository.save(pedido);
    }

    public Pedido adicionarProdutosPedido(UUID idPedido, UUID idProduto) {
        Pedido pedido = pedidoRepository.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        pedido.getProdutoList().add(produto);
        pedido.setQuantidadeTotalDeItems(pedido.getProdutoList().size());
        pedido.setValorTotalDaCompra(pedido.getValorTotalDaCompra().add(produto.getPreco()));
        return pedidoRepository.save(pedido);
    }

    public Pedido removerProdutosPedido(UUID idPedido, UUID idProduto) {
        Pedido pedido = pedidoRepository.findByIdAndStatusPedido(idPedido, StatusPedido.ABERTO)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        Produto produtoToRemove = pedido.getProdutoList().stream().filter(x -> x.getId().equals(idProduto)).findFirst()
                .orElseThrow(() -> new NotFoundException("Produto não encontrado no pedido"));
        pedido.getProdutoList().remove(produtoToRemove);
        pedido.setQuantidadeTotalDeItems(pedido.getProdutoList().size());
        pedido.setValorTotalDaCompra(pedido.getValorTotalDaCompra().subtract(produtoToRemove.getPreco()));
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidosNaoFinalizados() {
        return pedidoRepository.findAllExcept(Arrays.asList(StatusPedido.PEDIDO_RETIRADO, StatusPedido.CANCELADO));
    }

    public void alterarStatusPedido(UUID id, StatusPedido statusPedido) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (!statusPedido.equals(StatusPedido.CANCELADO)
                && pedido.getStatusPagamento().equals(StatusPagamento.AGUARDANDO_PAGAMENTO)) {
            throw new PaymentNotApprovedException("Alteração de status não permitida, pois o pagamento não efetuado");
        }

        StatusPedido.verifyOrderOnUpdate(pedido.getStatusPedido(), statusPedido);

        pedido.setStatusPedido(statusPedido);
        pedidoRepository.save(pedido);
    }

}
