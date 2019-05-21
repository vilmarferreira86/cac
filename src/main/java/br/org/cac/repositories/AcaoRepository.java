package br.org.cac.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.org.cac.models.Acao;

/**
* Generated by Spring Data Generator on 16/03/2019
*/
@Repository
public interface AcaoRepository extends JpaRepository<Acao, Integer>, JpaSpecificationExecutor<Acao> {
	
	Page<Acao> findByNomeOrDescricaoContaining(String nome, String descricao, Pageable pageable);

}