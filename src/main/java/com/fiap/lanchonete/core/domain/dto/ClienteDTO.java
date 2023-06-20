package com.fiap.lanchonete.core.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {

    private String nome;
    private String cpf;
    private String telefone;
}
