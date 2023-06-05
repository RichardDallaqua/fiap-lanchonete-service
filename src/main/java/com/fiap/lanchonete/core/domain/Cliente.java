package com.fiap.lanchonete.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "clientes")
public class Cliente {

    @Id
    UUID id;
    String nome;
    CPF cpf;
    String telefone;
    LocalDate data;
}
