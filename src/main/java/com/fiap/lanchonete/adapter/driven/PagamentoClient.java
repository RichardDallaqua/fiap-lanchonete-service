package com.fiap.lanchonete.adapter.driven;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fiap.lanchonete.core.domain.dto.PagamentoResponse;

@Component
public class PagamentoClient {

    private static final String URL_MERCADO_PAGO = "https://007z3.mocklab.io/realizarpagamento/";

    public PagamentoResponse realizarPagamento(UUID idPedido) {

        WebClient webClient = WebClient.create();
        return webClient.get().uri(URL_MERCADO_PAGO.concat(idPedido.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PagamentoResponse.class).block();
    }

}
