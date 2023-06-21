package com.fiap.lanchonete.adapter.driver;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.lanchonete.core.applications.services.PagamentoService;
import com.fiap.lanchonete.core.domain.dto.PagamentoResponse;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PutMapping("/{idPedido}/realizarPagamento")
    public ResponseEntity<PagamentoResponse> realizarPagamento(@PathVariable("idPedido") UUID idPedido) {

        return ResponseEntity.ok().body(pagamentoService.realizarPagamento(idPedido));
    }
}