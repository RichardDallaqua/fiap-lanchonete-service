package com.fiap.lanchonete.adapter.driver;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.lanchonete.core.applications.services.PagamentoService;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PutMapping("/{idPedido}/realizarPagamento")
    public ResponseEntity<Void> realizarPagamento(@PathVariable("idPedido") UUID idPedido) {
        pagamentoService.realizarPagamento(pagamentoService, idPedido);
        return ResponseEntity.noContent().build();
    }
}