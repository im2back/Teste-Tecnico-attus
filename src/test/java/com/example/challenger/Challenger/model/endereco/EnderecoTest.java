package com.example.challenger.Challenger.model.endereco;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;

class EnderecoTest {


	    @Test
	    void testUpdateEnderecoCompleto() {
	        // arrangr
	        Endereco endereco = new Endereco();
	        EnderecoDto dto = new EnderecoDto(1l,2l,"Residencial", "12345", "100", "Sao Paulo", "SP", true);

	        // act
	        endereco.updateEndereco(dto);

	        // assert
	        assertEquals("Residencial", endereco.getLogradouro());
	        assertEquals("Sao Paulo", endereco.getCidade());
	        assertEquals("SP", endereco.getEstado());
	        assertEquals("100", endereco.getNumero());
	        assertEquals(true, endereco.isPrincipal());
	    }

		
	}


