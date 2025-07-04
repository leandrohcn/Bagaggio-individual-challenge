package baggagio.com.individual_challenge.dtos;

import lombok.Data;

//DTO para criação e atualização de produtos
@Data
public class ProdutoDTO {
    private String nome;
    private String descricao;
    private Float preco;
    private Integer quantidade;
}
