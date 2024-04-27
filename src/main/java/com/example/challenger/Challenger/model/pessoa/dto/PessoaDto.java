package com.example.challenger.Challenger.model.pessoa.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.challenger.Challenger.model.endereco.Endereco;

public record PessoaDto(
		
		Long id,
		
		String nomeCompleto,

		LocalDate dataNascimento,
		
		List<Endereco> endereco
		
		) {

}
