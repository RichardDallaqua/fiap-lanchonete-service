package com.fiap.lanchonete.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.lanchonete.controller.dto.PagamentoResponseDTO;
import com.fiap.lanchonete.services.PagamentoService;

@RestController
@RequestMapping("api/v1/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PutMapping("/{idPedido}/realizarPagamento")
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(@PathVariable("idPedido") UUID idPedido) {
        pagamentoService.realizarPagamento(idPedido);
        return ResponseEntity.accepted().build();
    }
}