package com.example.challenger.Challenger.model.endereco.dto;

import com.example.challenger.Challenger.model.endereco.Endereco;

public record EnderecoDto(
		Long idEndereco,
		Long pessoaId,
		String logradouro,
		String cep,
		String numero,
		String cidade,
		String estado,
		boolean principal
		
		) {
	
	 public EnderecoDto(Endereco endereco) {
	        this(
	            endereco.getId(),
	            endereco.getPessoa().getId(),
	            endereco.getLogradouro(),
	            endereco.getCep(),
	            endereco.getNumero(),
	            endereco.getCidade(),
	            endereco.getEstado(),
	            endereco.isPrincipal()
	        );
	    }
}
