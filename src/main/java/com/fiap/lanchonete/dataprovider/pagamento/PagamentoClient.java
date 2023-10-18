package com.fiap.lanchonete.dataprovider.pagamento;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.lanchonete.webhook.WebhookProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fiap.lanchonete.controller.dto.PagamentoResponseDTO;

@Component
@Slf4j
public class PagamentoClient {

    private static final String URL_MERCADO_PAGO = "https://007z3.wiremockapi.cloud/realizarpagamento/?id=";

    @Autowired
    private WebhookProducer webhookProducer;

    @Autowired
    private ObjectMapper objectMapper;

    public void realizarPagamento(UUID idPedido) {

        WebClient webClient = WebClient.create();
        PagamentoResponseDTO pagamentoResponseDTO = webClient.get().uri(URL_MERCADO_PAGO.concat(idPedido.toString())).accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(PagamentoResponseDTO.class).block();
        pagamentoResponseDTO.setIdPedido(idPedido);
        sendPaymentWebhook(pagamentoResponseDTO);
    }

    private void sendPaymentWebhook(PagamentoResponseDTO pagamentoResponseDTO){
        try{
            webhookProducer.sendMessage(objectMapper.writeValueAsString(pagamentoResponseDTO));
        }catch (Exception ex){
            log.error("Erro ao enviar notificação de pagamento");
        }
    }

}
