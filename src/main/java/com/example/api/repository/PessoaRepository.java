package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.api.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Integer>{
	
	@Query("select e from Pessoa e where e.nome like %:nome%")
	public List<Pessoa> findByName(@Param("nome") String nome); 
}
