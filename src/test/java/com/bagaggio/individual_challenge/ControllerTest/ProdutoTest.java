package com.bagaggio.individual_challenge.ControllerTest;

import com.bagaggio.individual_challenge.controller.ProdutoController;
import com.bagaggio.individual_challenge.dtos.AtualizaProdutoDTO;
import com.bagaggio.individual_challenge.dtos.ProdutoDTO;
import com.bagaggio.individual_challenge.dtos.ProdutoResponseDTO;
import com.bagaggio.individual_challenge.entities.Categoria;
import com.bagaggio.individual_challenge.entities.Produto;
import com.bagaggio.individual_challenge.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoTest {
    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Mala", "Grande", 85, 25, Categoria.MALAS);
        produto.setId(1);
    }

    @Test
    @DisplayName("Deve buscar todos os produtos com sucesso quando nenhuma categoria é fornecida")
    void buscaTodosProdutos_CaminhoFeliz_SemCategoria() {
        when(produtoService.listarTodosProdutos()).thenReturn(List.of(produto));

        ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.buscaTodosProdutos(null);
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().size()).isEqualTo(1);
        assertThat(resposta.getBody().get(0).getId()).isEqualTo(produto.getId());
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria com sucesso")
    void buscaTodosProdutos_CaminhoFeliz_ComCategoria() {
        when(produtoService.listarPorCategoria(Categoria.MALAS)).thenReturn(List.of(produto));


        ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.buscaTodosProdutos(Categoria.MALAS);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().size()).isEqualTo(1);
        assertThat(resposta.getBody().get(0).getCategoria()).isEqualTo(Categoria.MALAS);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos")
    void buscaTodosProdutos_CaminhoTriste_ListaVazia() {
        when(produtoService.listarTodosProdutos()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.buscaTodosProdutos(null);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Deve buscar produto por ID com sucesso")
    void buscaProdutoPorId_CaminhoFeliz() {
        when(produtoService.buscarProdutoPorId(1)).thenReturn(produto);

        ResponseEntity<ProdutoResponseDTO> resposta = produtoController.buscaProdutoPorId(1);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getId()).isEqualTo(produto.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar produto por ID inexistente")
    void buscaProdutoPorId_CaminhoTriste_NaoEncontrado() {
        when(produtoService.buscarProdutoPorId(99)).thenThrow(new RuntimeException("Produto não encontrado"));

        assertThrows(RuntimeException.class, () -> produtoController.buscaProdutoPorId(99));
    }

    @Test
    @DisplayName("Deve criar um novo produto com sucesso")
    void criaProduto_CaminhoFeliz() {
        float preco = 300;
        ProdutoDTO novoProdutoDTO = new ProdutoDTO();
        novoProdutoDTO.setCategoria(Categoria.MALAS);
        novoProdutoDTO.setNome("Mala Nova");
        novoProdutoDTO.setDescricao("Pequena");
        novoProdutoDTO.setPreco(preco);
        novoProdutoDTO.setQuantidade(10);

        // O service deve retornar um DTO de resposta com o produto já criado (incluindo ID)
        Produto produtoCriado = new Produto(novoProdutoDTO.getNome(), novoProdutoDTO.getDescricao(), novoProdutoDTO.getPreco(),
                novoProdutoDTO.getQuantidade(), novoProdutoDTO.getCategoria());
        produtoCriado.setId(2); // Simula o ID gerado pelo banco
        ProdutoResponseDTO responseDtoEsperado = new ProdutoResponseDTO(produtoCriado);

        when(produtoService.criarProduto(any(ProdutoDTO.class))).thenReturn(responseDtoEsperado);

        ResponseEntity<ProdutoResponseDTO> resposta = produtoController.criaProduto(novoProdutoDTO);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getId()).isEqualTo(2);
        assertThat(resposta.getBody().getNome()).isEqualTo("Mala Nova");
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso")
    void atualizaProduto_CaminhoFeliz() {
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(1);
        produtoAtualizado.setCategoria(Categoria.MALAS);
        produtoAtualizado.setPreco(33.0f);
        produtoAtualizado.setQuantidade(10);
        produtoAtualizado.setDescricao("Pequena");
        produtoAtualizado.setNome("Mala Nova");
        float novoPreco = 150.0f;
        AtualizaProdutoDTO produtoAtualizadoDTO = new AtualizaProdutoDTO();
        produtoAtualizadoDTO.setNome("Bolsa Chique");
        produtoAtualizadoDTO.setPreco(novoPreco);
        produtoAtualizado.setNome(produtoAtualizadoDTO.getNome());
        produtoAtualizado.setPreco(produtoAtualizadoDTO.getPreco());

        ProdutoResponseDTO responseDtoAtualizado = new ProdutoResponseDTO(produtoAtualizado);

        when(produtoService.atualizarProduto(any(AtualizaProdutoDTO.class), eq(1))).thenReturn(responseDtoAtualizado);
        ResponseEntity<ProdutoResponseDTO> resposta = produtoController.atualizaProduto(1, produtoAtualizadoDTO);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getPreco()).isEqualTo(novoPreco);
        assertThat(resposta.getBody().getNome()).isEqualTo("Bolsa Chique");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar produto inexistente")
    void atualizaProduto_CaminhoTriste_NaoEncontrado() {
        AtualizaProdutoDTO produtoAtualizadoDTO = new AtualizaProdutoDTO();
        when(produtoService.atualizarProduto(any(AtualizaProdutoDTO.class), eq(99)))
                .thenThrow(new RuntimeException("Produto não encontrado para atualização"));

        assertThrows(RuntimeException.class, () -> produtoController.atualizaProduto(99, produtoAtualizadoDTO));
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    void deletaProduto_CaminhoFeliz() {
        doNothing().when(produtoService).excluirProduto(1);

        ResponseEntity<Void> resposta = produtoController.deletaProduto(1);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(produtoService, times(1)).excluirProduto(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar produto inexistente")
    void deletaProduto_CaminhoTriste_NaoEncontrado() {
        doThrow(new RuntimeException("ID do produto não encontrado para exclusão"))
                .when(produtoService).excluirProduto(99);

        assertThrows(RuntimeException.class, () -> produtoController.deletaProduto(99));
        verify(produtoService, times(1)).excluirProduto(99);
    }
}
