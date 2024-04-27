package com.example.challenger.Challenger.model.endereco.dto;

public record EnderecoCadastroDto(
	
	Long idPessoa,	
	String logradouro,
	String cep,
	String numero,
	String cidade,
	String estado,
	boolean principal
		
		) {

}
