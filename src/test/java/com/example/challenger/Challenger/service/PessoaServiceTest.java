package com.example.challenger.Challenger.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.challenger.Challenger.model.endereco.Endereco;
import com.example.challenger.Challenger.model.pessoa.Pessoa;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaCadastroDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaDto;
import com.example.challenger.Challenger.repository.PessoaRepository;
import com.example.challenger.Challenger.service.exceptions.ResourceNotFound;
import com.example.challenger.Challenger.util.UtilTest;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {
	
	@Mock	
	private PessoaRepository repository;
	
	@InjectMocks
	private PessoaService service;
	
	@Captor
	private ArgumentCaptor<List<Pessoa>> pessoaListCaptor;
	
	@Captor
	private ArgumentCaptor<Pessoa> pessoaCaptor;
	
	@Captor
	private ArgumentCaptor<Long> idCaptor;

	@Test
	@DisplayName("deveria buscar que possua o id igual ao do parâmetro")
	void buscarPessoaPorId() {	
		//ARRANGE
		Long id = 1l;			
		var pessoa = Optional.ofNullable((UtilTest.MARIA));	
		BDDMockito.when(repository.findById(id)).thenReturn(pessoa);
		
		//act
		var response = service.buscarPessoaPorId(id);
		
		// ASSERT
		BDDMockito.then(repository).should().findById(idCaptor.capture());
		assertNotNull(response, "Não deve ser nulo");
		assertEquals(id, idCaptor.getValue());
		assertEquals(response.getNomeCompleto(), pessoa.get().getNomeCompleto());		
	}
	
	@Test
	@DisplayName("Deveria retornar uma exceção ResourceNotFound ao retornar uma pessoa nulo")
	void buscarPessoaPorIdCenario02() {
		//ARRANGE
		Long id = 1l;
						
		Optional<Pessoa> pessoa = Optional.ofNullable(null);	
		BDDMockito.when(repository.findById(id)).thenReturn(pessoa);
		
		// ACT & ASSERT
	    Assertions.assertThrows(ResourceNotFound.class, () -> {
	        service.buscarPessoaPorId(id);
	    });						
	}
	
	@Test
	@DisplayName("Deveria retornar uma lista do tipo List<PessoaDto>")
	void listarTodos() {		
		//ARRANGE				
		List<Pessoa> listPessoa = new ArrayList<>(Arrays.asList(UtilTest.MARIA,UtilTest.CARLOS));
		BDDMockito.when(repository.findAll()).thenReturn(listPessoa);
		
		// ACT 
	 	var response = service.listarTodos();	
	 	
	 	// ASSERT
	 	assertNotNull(response, "A resposta não deve ser nula");
	    assertFalse(response.isEmpty(), "A lista não deve estar vazia");
	    assertEquals(2, response.size(), "A lista deve conter dois elementos");
	    assertTrue(response.stream().anyMatch(p -> p.nomeCompleto().equals("maria")), "A lista deve conter Maria");
	    assertTrue(response.stream().anyMatch(p -> p.nomeCompleto().equals("carlos")), "A lista deve conter Carlos");
	    Mockito.verify(repository).findAll();
	}
	
	@Test
	@DisplayName("Deveria Salvar uma lista de pessoas e retornar um dto do tipo List<Pessoa>")
	void cadastrarPessoas() {
		
		//ARRANGE	 List<PessoaCadastroDto>	
		Pessoa maria = new Pessoa("maria", LocalDate.of(1975, 5, 20), null);
	    Pessoa carlos = new Pessoa("carlos", LocalDate.of(1980, 6, 15), null);
				
		Endereco endereco1 = new Endereco("Residencial", "67115810", "06", "Belem", "PA", false, null);
        Endereco endereco2 = new Endereco("Comercial", "01000-000", "100", "Sao Paulo", "SP", true, null);
        Endereco endereco3 = new Endereco("Residencial", "40010-020", "250", "Salvador", "BA", false, null);
        Endereco endereco4 = new Endereco("Rural", "75570-000", "S/N", "Catalao", "GO", false, null);
		
		List<Endereco> enderecosMaria = new ArrayList<>(Arrays.asList(endereco1,endereco2));
		List<Endereco> enderecosCarlos = new ArrayList<>(Arrays.asList(endereco3,endereco4));

		maria.setEndereco(enderecosMaria);
		carlos.setEndereco(enderecosCarlos);
		
		List<Pessoa> listPessoa = new ArrayList<>(Arrays.asList(maria,carlos));
		
		PessoaCadastroDto pessoaCadastroDto1 = new PessoaCadastroDto("maria",LocalDate.of(1975, 5, 20),enderecosMaria);
		PessoaCadastroDto pessoaCadastroDto2 = new PessoaCadastroDto("carlos",LocalDate.of(1980, 6, 15),enderecosCarlos);
		List<PessoaCadastroDto> dtoParametro = new ArrayList<>(Arrays.asList(pessoaCadastroDto1,pessoaCadastroDto2));						
		
		// ACT 
	 	var response = service.cadastrarPessoas(dtoParametro);	
	 	
	 	// assert
	 	BDDMockito.then(repository).should().saveAll(pessoaListCaptor.capture()); 
	 	var captured = pessoaListCaptor.getValue();
	 	assertEquals(listPessoa, captured);  
	 	
	 	assertNotNull(response, "A resposta não deve ser nula");
	    assertFalse(response.isEmpty(), "A lista não deve estar vazia");
	    assertEquals(2, response.size(), "A lista deve conter dois elementos");
	    assertTrue(response.stream().anyMatch(p -> p.getNomeCompleto().equals("maria")), "A lista deve conter Maria");
	    assertTrue(response.stream().anyMatch(p -> p.getNomeCompleto().equals("carlos")), "A lista deve conter Carlos");	
	}
	
	@Test
	@DisplayName("Deveria Salvar as modificações e retornar um dto do tipo List<PessoaEditarDadosPessoaisDto> ")
	void editarPessoas(){		
		//ARRANGE	
		Optional<Pessoa> maria = Optional.ofNullable((UtilTest.MARIA));
	    Optional< Pessoa> carlos = Optional.ofNullable((UtilTest.CARLOS));
	    			
		PessoaDto pessoaDto1 = new PessoaDto(maria.get().getId(),maria.get().getNomeCompleto(),maria.get().getDataNascimento(),maria.get().getEndereco()); 
		PessoaDto pessoaDto2 = new PessoaDto(carlos.get().getId(),carlos.get().getNomeCompleto(),carlos.get().getDataNascimento(),carlos.get().getEndereco()); 	
		List<PessoaDto> dtoParametro = new ArrayList<>(Arrays.asList(pessoaDto2, pessoaDto1));	
		
		BDDMockito.when(repository.findById(maria.get().getId())).thenReturn(maria);
		BDDMockito.when(repository.findById(carlos.get().getId())).thenReturn(carlos);
		
		// ACT 
	 	var response = service.editarPessoas(dtoParametro);	
	 	
	 	// assert
	    verify(repository).findById(1L);
        verify(repository).findById(2L);
        verify(repository).save(maria.get());
        verify(repository).save(carlos.get());
 	
	 	assertNotNull(response, "A resposta não deve ser nula");
	    assertFalse(response.isEmpty(), "A lista não deve estar vazia");
	    assertEquals(2, response.size(), "A lista deve conter dois elementos");
	    assertTrue(response.stream().anyMatch(p -> p.nomeCompleto().equals("maria")), "A lista deve conter Maria");
	    assertTrue(response.stream().anyMatch(p -> p.nomeCompleto().equals("carlos")), "A lista deve conter Carlos");	
	}

}
