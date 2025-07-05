package com.baggaggio.individual_challenge.repositories;

import com.baggaggio.individual_challenge.entities.Categoria;
import com.baggaggio.individual_challenge.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repository que "extend" o JpaRepositry = facilita a persistência de dados (simplifica o acesso ao banco)
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByNome(String nome);
    List<Produto> findByCategoria(Categoria categoria);
}
