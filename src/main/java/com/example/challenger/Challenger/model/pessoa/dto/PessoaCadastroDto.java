package com.example.challenger.Challenger.model.pessoa.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.challenger.Challenger.model.endereco.Endereco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PessoaCadastroDto(
		
		@NotNull
		@Size(min = 5)
		String nomeCompleto,
		
		LocalDate dataNascimento,
		
		List<Endereco> endereco
		) {

}
