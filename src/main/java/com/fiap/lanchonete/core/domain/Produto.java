package com.fiap.lanchonete.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "produtos")
public class Produto {

    @Id
    private UUID id;
    private String nome;
    private String descricao;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal preco;
    private CategoriaProduto categoria;
}
