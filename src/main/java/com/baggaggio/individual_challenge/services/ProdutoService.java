package com.baggaggio.individual_challenge.services;

import com.baggaggio.individual_challenge.dtos.ProdutoDTO;
import com.baggaggio.individual_challenge.dtos.ProdutoResponseDTO;
import com.baggaggio.individual_challenge.entities.Produto;
import com.baggaggio.individual_challenge.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

//Classe de serviços / classe destinada á logicas de negocio
@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    //Lista todos os produtos
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    //Busca produto por id e se não achou lança a excessão de notFound
    public Produto buscarProdutoPorId(Integer id) {
        return produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    // Produto sendo criado através da passagem de DTO no corpo da requisição, salvando a entidade e retornando o dto de response diretamente
    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto novoProduto = new Produto(
                produtoDTO.getNome(),
                produtoDTO.getDescricao(),
                produtoDTO.getPreco(),
                produtoDTO.getQuantidade()
        );
        Produto produtoCriado = produtoRepository.save(novoProduto);
        return new ProdutoResponseDTO(produtoCriado);
    }

    //Mesma ideia do criar, porem tratando de não deixar campos nulos na tabela e nem na resposta. Pode atualizar 1 ou mais atributos
    @Transactional
    public ProdutoResponseDTO atualizarProduto(ProdutoDTO atualizaProduto, Integer id) {
        Produto produto = buscarProdutoPorId(id);
        if (atualizaProduto.getDescricao() != null) {
            produto.setDescricao(atualizaProduto.getDescricao());
        }
        if (atualizaProduto.getNome() != null) {
            produto.setNome(atualizaProduto.getNome());
        }
        if (atualizaProduto.getPreco() != null) {
            produto.setPreco(atualizaProduto.getPreco());
        }
        if (atualizaProduto.getQuantidade() != null) {
            produto.setQuantidade(atualizaProduto.getQuantidade());
        }

        Produto produtoAtualizado = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoAtualizado);
    }

    // primeiro procura se existe e depois deleta, se não existe lança uma excessão de NotFound
    public void excluirProduto(Integer id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        }
        throw new EntityNotFoundException("Produto não encontrado");
    }

}
