package com.bagaggio.individual_challenge.services;

import com.bagaggio.individual_challenge.dtos.AtualizaProdutoDTO;
import com.bagaggio.individual_challenge.dtos.ProdutoDTO;
import com.bagaggio.individual_challenge.dtos.ProdutoResponseDTO;
import com.bagaggio.individual_challenge.entities.Categoria;
import com.bagaggio.individual_challenge.entities.Produto;
import com.bagaggio.individual_challenge.repositories.ProdutoRepository;
import com.bagaggio.individual_challenge.exceptions.ConflitoDeRecursoException;
import com.bagaggio.individual_challenge.exceptions.RecursoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Lista todos os produtos e se não tiver nenhum retorna uma lista vazia
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    // Lista todos os produtos de acordo com a categoria passada no parametro da requisição
    public List<Produto> listarPorCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    // Busca produto por id e retorna ele
    public Produto buscarProdutoPorId(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id));
    }

    // Cria um novo produto e salva no banco
    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoDTO produtoDTO) {
        validaNomeUnicoParaCriacao(produtoDTO.getNome());
        Produto novoProduto = new Produto(
                produtoDTO.getNome(),
                produtoDTO.getDescricao(),
                produtoDTO.getPreco(),
                produtoDTO.getQuantidade(),
                produtoDTO.getCategoria()
        );
        Produto produtoCriado = produtoRepository.save(novoProduto);
        return new ProdutoResponseDTO(produtoCriado);
    }

    //Atualiza produto de acordo com algumas regras de negocio
    @Transactional
    public ProdutoResponseDTO atualizarProduto(AtualizaProdutoDTO atualizaProduto, Integer id) {
        Produto produto = buscarProdutoPorId(id); // Já trata o 'não encontrado'
        validaNomeUnicoParaAtualizacao(atualizaProduto.getNome(), id);
        atualizaDadosDoProduto(produto, atualizaProduto); // Lógica encapsulada

        Produto produtoAtualizado = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoAtualizado);
    }

    // Exclui um produto de acordo com o id passado
    public void excluirProduto(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    //Achei que não fazia sentido produtos poderem ter o mesmo nome então fiz uma validação
    private void validaNomeUnicoParaCriacao(String nome){
        if (produtoRepository.findByNome(nome).isPresent()) {
            throw new ConflitoDeRecursoException("Já existe um produto com o nome: " + nome);
        }
    }

    // mesma ideia da validação acima, porém com algumas alterações
    private void validaNomeUnicoParaAtualizacao(String nome, Integer idAtual) {
        if (nome == null) return; // Se o nome não está sendo atualizado, não faz nada

        Optional<Produto> produtoExistente = produtoRepository.findByNome(nome);
        // Se existe um produto com esse nome E o ID dele é diferente do que estamos atualizando, então é um conflito.
        if (produtoExistente.isPresent() && !produtoExistente.get().getId().equals(idAtual)) {
            throw new ConflitoDeRecursoException("Já existe outro produto com o nome: " + nome);
        }
    }

    /*
        Logica que garante que eu possa atualizar um ou mais dados do produto, sem que os outros campos fiquem nulos ou em branco
        caso eu passe somente um ou mais dados, que não todos, ou até mesmo deixe em branco.
     */
    private void atualizaDadosDoProduto(Produto produto, AtualizaProdutoDTO atualizaProduto) {
        if (!atualizaProduto.getDescricao().isBlank()) {
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
        if (atualizaProduto.getCategoria() != null) {
            produto.setCategoria(atualizaProduto.getCategoria());
        }
    }
}