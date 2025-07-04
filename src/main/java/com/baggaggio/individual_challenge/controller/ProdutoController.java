package com.baggaggio.individual_challenge.controller;

import com.baggaggio.individual_challenge.dtos.ProdutoDTO;
import com.baggaggio.individual_challenge.dtos.ProdutoResponseDTO;
import com.baggaggio.individual_challenge.entities.Produto;
import com.baggaggio.individual_challenge.services.ProdutoService;
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
    // Requisicoes
    @GetMapping
    public ResponseEntity<List<Produto>> buscaTodosProdutos() {
        List<Produto> produtos = produtoService.listarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Produto> buscaProdutoPorId(@PathVariable Integer id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criaProduto(@RequestBody ProdutoDTO novoProduto) {
        ProdutoResponseDTO produto = produtoService.criarProduto(novoProduto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizaProduto(@PathVariable Integer id, @RequestBody ProdutoDTO novoProduto) {
        ProdutoResponseDTO produto = produtoService.atualizarProduto(novoProduto, id);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaProduto(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok().build();
    }

}
