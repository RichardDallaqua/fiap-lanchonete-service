package com.fiap.lanchonete.core.domain.dto;

import com.fiap.lanchonete.core.domain.type.CategoriaProduto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo")
    private String nome;
    @NotBlank(message = "Descrição não pode ser vazio ou nulo")
    private String descricao;
    @DecimalMin(value = "0", inclusive = false, message = "Preço não pode ser menor ou igual a zero")
    private BigDecimal preco;
    @NotNull(message = "Categoria não pode ser nulo")
    private CategoriaProduto categoria;
}
