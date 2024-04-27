package com.example.challenger.Challenger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenger.Challenger.model.endereco.Endereco;

public interface EnderecoRepository  extends JpaRepository<Endereco, Long>{


	List<Endereco> findByPessoaId(Long pessoaId);

}
