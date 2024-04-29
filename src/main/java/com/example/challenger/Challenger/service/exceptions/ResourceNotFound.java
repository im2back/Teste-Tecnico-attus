package com.example.challenger.Challenger.service.exceptions;

public class ResourceNotFound extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public ResourceNotFound (Long id) {
		super("Recurso não encontrado para o ID : " + id ); 
	}

	
}
