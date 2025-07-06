package com.bagaggio.individual_challenge.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entidade que será mapeada para tabela no meu sgbd
@Entity
@Data
@NoArgsConstructor
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String nome;
    private String descricao;
    private Float preco;
    private Integer quantidade;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Produto(String nome, String descricao, float preco, int quantidade, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }
}
