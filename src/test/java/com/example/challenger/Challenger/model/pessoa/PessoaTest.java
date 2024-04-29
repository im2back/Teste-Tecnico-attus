package com.example.challenger.Challenger.model.pessoa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import com.example.challenger.Challenger.model.pessoa.dto.PessoaDto;

class PessoaTest {

	 @Test
	    void testUpdateNomeCompleto() {
	        // ACT
	        Pessoa pessoa = new Pessoa();
	        PessoaDto dto = new PessoaDto(1l,"João Silva", LocalDate.of(1990, 1, 1),null);

	        // ARRANGE
	        pessoa.update(dto);

	        // ASSERT
	        assertEquals("João Silva", pessoa.getNomeCompleto());
	    }

	    @Test
	    void testUpdateDataNascimento() {
	        // ARRANGE
	        Pessoa pessoa = new Pessoa();
	        PessoaDto dto = new PessoaDto(1l,"João Silva", LocalDate.of(1990, 1, 1),null);

	        // ACT
	        pessoa.update(dto);

	        // ASSERT
	        assertEquals(LocalDate.of(1990, 1, 1), pessoa.getDataNascimento());
	    }

}
