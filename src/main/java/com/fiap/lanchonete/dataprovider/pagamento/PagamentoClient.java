package com.fiap.lanchonete.dataprovider.pagamento;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fiap.lanchonete.controller.dto.PagamentoResponseDTO;

@Component
public class PagamentoClient {

    private static final String URL_MERCADO_PAGO = "https://007z3.mocklab.io/realizarpagamento/?id=";

    public PagamentoResponseDTO realizarPagamento(UUID idPedido) {

        WebClient webClient = WebClient.create();
        return webClient.get().uri(URL_MERCADO_PAGO.concat(idPedido.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PagamentoResponseDTO.class).block();
    }

}
