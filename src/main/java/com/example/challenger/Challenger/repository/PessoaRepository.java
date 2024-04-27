package com.example.challenger.Challenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenger.Challenger.model.pessoa.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, Long>{

}
