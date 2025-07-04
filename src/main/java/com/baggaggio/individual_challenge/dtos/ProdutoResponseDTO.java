package com.baggaggio.individual_challenge.dtos;

import com.baggaggio.individual_challenge.entities.Produto;
import lombok.Data;

//DTO destinado para resposta da requisição
@Data
public class ProdutoResponseDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private float preco;
    private int quantidade;
//Construtor para que ao inves de passar a entidade na resposta, eu passe o DTO.
//Por mais que não tenha dados sensíveis nem nada do tipo.
    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.quantidade = produto.getQuantidade();
    }
}
