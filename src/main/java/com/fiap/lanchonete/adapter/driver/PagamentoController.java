package com.fiap.lanchonete.adapter.driver;

import com.fiap.lanchonete.core.applications.services.PagamentoService;
import com.fiap.lanchonete.core.applications.services.PedidoService;
import com.fiap.lanchonete.core.domain.Pedido;
import com.fiap.lanchonete.core.domain.type.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PutMapping("/{idPedido}/realizarPagamento")
    public ResponseEntity<Void> realizarPagamento(@PathVariable("idPedido") UUID idPedido){
        pagamentoService.realizarPagamento(idPedido);
        return ResponseEntity.noContent().build();
    }
}
