package com.baggaggio.individual_challenge.dtos;

import com.baggaggio.individual_challenge.entities.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

//DTO para criação de produtos
@Data
@Schema(description = "Representa dados que vão ser passados no corpo da requisição")
public class ProdutoDTO {
    @JsonProperty(required = true)
    @NotBlank
    private String nome;
    @JsonProperty(required = true)
    @NotBlank
    private String descricao;
    @JsonProperty(required = true)
    @NotNull(message = "O preço não pode ser null.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Float preco;
    @JsonProperty(required = true)
    @NotNull(message = "A quantidade não pode ser null.")
    @Positive(message = "Quantidade deve ser um valor positivo.")
    private Integer quantidade;
    @JsonProperty(required = true)
    @NotNull(message = "Categoria não pode ser null.")
    private Categoria categoria;
}
