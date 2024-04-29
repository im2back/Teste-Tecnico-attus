package com.example.challenger.Challenger.model.pessoa.dto;

import java.time.LocalDate;

import com.example.challenger.Challenger.model.pessoa.Pessoa;

import jakarta.validation.constraints.NotNull;

public record PessoaEditarDadosPessoaisDto(
		
		@NotNull
		Long id,
		
		String nomeCompleto,

		LocalDate dataNascimento
		) {
	
	 public PessoaEditarDadosPessoaisDto(Pessoa pessoa) {
	        this(pessoa.getId(),pessoa.getNomeCompleto(),pessoa.getDataNascimento());
	    }
}


