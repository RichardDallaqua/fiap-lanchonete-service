package com.fiap.lanchonete.adapter.driver;

import com.fiap.lanchonete.core.applications.services.PedidoService;
import com.fiap.lanchonete.core.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> iniciarPedido(@RequestParam(value = "cpf", required = false) String cpf){
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.iniciarPedido(cpf));
    }

    @PutMapping("/{idPedido}/adicionar/{idProduto}")
    public ResponseEntity<Pedido> adicionarProdutos(@PathVariable("idPedido") UUID idPedido, @PathVariable("idProduto") UUID idProduto){
        return ResponseEntity.ok(pedidoService.adicionarProdutosPedido(idPedido, idProduto));
    }
}
