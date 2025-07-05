package com.baggaggio.individual_challenge.dtos;

import com.baggaggio.individual_challenge.entities.Categoria;
import com.baggaggio.individual_challenge.entities.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//DTO destinado para resposta da requisição
@Data
@Schema(description = "Representa a resposta que vai ser passada na requisição")
public class ProdutoResponseDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private float preco;
    private int quantidade;
    private Categoria categoria;
//Construtor para que ao inves de passar a entidade na resposta, eu passe o DTO.
//Por mais que não tenha dados sensíveis nem nada do tipo.
    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.quantidade = produto.getQuantidade();
        this.categoria = produto.getCategoria();
    }
}
