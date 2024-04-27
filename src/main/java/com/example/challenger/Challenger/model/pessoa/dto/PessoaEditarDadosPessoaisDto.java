package com.example.challenger.Challenger.model.pessoa.dto;

import java.time.LocalDate;

import com.example.challenger.Challenger.model.pessoa.Pessoa;

public record PessoaEditarDadosPessoaisDto(
		Long id,
		
		String nomeCompleto,

		LocalDate dataNascimento
		) {
	
	 public PessoaEditarDadosPessoaisDto(Pessoa pessoa) {
	        this(pessoa.getId(),pessoa.getNomeCompleto(),pessoa.getDataNascimento());
	    }
}


