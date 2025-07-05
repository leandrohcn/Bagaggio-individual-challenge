package com.baggaggio.individual_challenge.dtos;

import com.baggaggio.individual_challenge.entities.Categoria;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO para atualização parcial de produtos via PUT
@Data
public class AtualizaProdutoDTO {
    // A validação permite que o campo seja nulo, mas se for enviado, não pode ser vazio.
    @Size(min = 1, message = "O nome, se fornecido, não pode ser vazio.")
    private String nome;

    private String descricao;

    @Positive(message = "O preço deve ser um valor positivo.")
    private Float preco;

    @Positive(message = "A quantidade deve ser um valor positivo.")
    private Integer quantidade;

    private Categoria categoria;
}
