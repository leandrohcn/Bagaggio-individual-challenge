package com.bagaggio.individual_challenge.controller;

import com.bagaggio.individual_challenge.dtos.AtualizaProdutoDTO;
import com.bagaggio.individual_challenge.dtos.ProdutoDTO;
import com.bagaggio.individual_challenge.dtos.ProdutoResponseDTO;
import com.bagaggio.individual_challenge.entities.Categoria;
import com.bagaggio.individual_challenge.entities.Produto;
import com.bagaggio.individual_challenge.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    /** delimitei ao service tratar das exceções de acordo com regras de negocio.
    E as excecoes estao sendo tratadas com @ExceptionHandler no pacote de exceptions, para uma melhor visualização
    ao deparar com erros em requisições.

     Requisicoes:
     As que precisam retornar algo, implementei o retorno para os DTOS,
     pois numa aplicação a camada de acesso a dados (geralmente um banco de dados)
     não precisa interagir com a camada de serviços, reduzindo o acoplamento. **/

    //Busca todos os produto
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> buscaTodosProdutos(@RequestParam (required = false) Categoria categoria) {
        List<Produto> produtos;
        if (categoria == null) {
            // se categoria nao passada na requisição, entao retorna todos ou nenhum produto
            produtos = produtoService.listarTodosProdutos();
        }
        else {
            // retorna todos os produtos que tem a categoria passada na requisição
            produtos = produtoService.listarPorCategoria(categoria);
        }

        List<ProdutoResponseDTO> resposta =  produtos.stream().map(ProdutoResponseDTO::new).toList();
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    //Busca produto por id passado como parametro e retorna caso encontrado
    @GetMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> buscaProdutoPorId(@PathVariable Integer id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        ProdutoResponseDTO resposta = new ProdutoResponseDTO(produto);
        return ResponseEntity.ok(resposta);
    }

    // cria um novo produto de acordo com as informações passadas no corpo da requisição pelo DTO de produto
    // retorna produto completo caso criação seja bem sucedida
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criaProduto(@Valid @RequestBody ProdutoDTO novoProduto) {
        ProdutoResponseDTO produto = produtoService.criarProduto(novoProduto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    /* Atualiza um produto, de acordo com o id passado por parametro, e informações passadas no corpo da requisição
        de acordo com o dto de atualização
     */
    @PutMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizaProduto(@PathVariable Integer id,
                                                              @Valid @RequestBody AtualizaProdutoDTO produtoAtualizado) {
        ProdutoResponseDTO produto = produtoService.atualizarProduto(produtoAtualizado, id);
        return ResponseEntity.ok(produto);
    }


    // Exclui um produto de acordo com o id passado por parametro e retorna ok
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaProduto(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok().build();
    }
}
