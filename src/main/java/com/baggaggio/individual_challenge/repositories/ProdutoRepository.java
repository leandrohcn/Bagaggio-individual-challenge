package com.baggaggio.individual_challenge.repositories;

import com.baggaggio.individual_challenge.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
// Repository que "extend" o JpaRepositry = facilita a persistência de dados (simplifica o acesso ao banco)
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
