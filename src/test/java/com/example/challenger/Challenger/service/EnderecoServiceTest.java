package com.example.challenger.Challenger.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

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
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.challenger.Challenger.model.endereco.Endereco;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoCadastroDto;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;
import com.example.challenger.Challenger.model.pessoa.Pessoa;
import com.example.challenger.Challenger.repository.EnderecoRepository;
import com.example.challenger.Challenger.service.exceptions.ResourceNotFound;
import com.example.challenger.Challenger.util.UtilTest;


@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {
	
			@Mock	
			private EnderecoRepository repository;

			@Mock	
			private PessoaService pessoaService;
			
			@InjectMocks
			private EnderecoService enderecoService;
			
			@Captor
			private ArgumentCaptor<List<Pessoa>> pessoaListCaptor;
			
			@Captor
			private ArgumentCaptor<List<Endereco>> enderecoListCaptor;
			
			@Captor
			private ArgumentCaptor<Endereco> enderecoCaptor;
			
			@Captor
			private ArgumentCaptor<Long> idCaptor;


	@Test
	@DisplayName("deveria retornar um Endereco que possua o id igual ao do parâmetroe retorna-lo")
	void buscarEnderecoPorId() {
		//ARRANGE
		Long id= 1l;
		
		var endereco = Optional.ofNullable((UtilTest.ENDERECO1));	
		BDDMockito.when(repository.findById(id)).thenReturn(endereco);
		
		//ACT
		var response = enderecoService.buscarEnderecoPorId(id);
					
		//ASSERT
		BDDMockito.then(repository).should().findById(idCaptor.capture());
		assertEquals(id, idCaptor.getValue());	
		assertNotNull(response, "Não deve ser nulo");
		assertEquals(response.getId(), id);			
	}
	
	@Test
	@DisplayName("deveria retornar uma exceção ao retornar um objeto nulo")
	void buscarEnderecoPorIdCenario02() {
		//ARRANGE
		Long id= 1l;
		
		Optional<Endereco> endereco = Optional.ofNullable((null));	
		BDDMockito.when(repository.findById(id)).thenReturn(endereco);
		
		// ACT & ASSERT
	    Assertions.assertThrows(ResourceNotFound.class, () -> {
	        enderecoService.buscarEnderecoPorId(id);
	    });			
	}
	
	@Test
	@DisplayName("deveria retornar um objeto do tipo List<EnderecoDto>")
	void buscarTodos() {
		//ARRANGE
		EnderecoDto enderecoDto1 = new EnderecoDto(UtilTest.ENDERECO5);
		EnderecoDto enderecoDto2 = new EnderecoDto(UtilTest.ENDERECO6);	
		List<EnderecoDto> respostaEsperada = new ArrayList<>(Arrays.asList(enderecoDto1,enderecoDto2));
		
		List<Endereco> responseRepository = new ArrayList<>(Arrays.asList(UtilTest.ENDERECO5,UtilTest.ENDERECO6));
		
		BDDMockito.when(repository.findAll()).thenReturn(responseRepository);
		
		//ACT
		var responseMethod = enderecoService.buscarTodos();
					
		//ASSERT
		assertNotNull(responseMethod, "Não deve ser nulo");
		assertEquals(responseMethod, respostaEsperada);			
	}
	
	@Test
	@DisplayName("deveria retornar um List<EnderecoDto> que possua o id igual ao do parâmetro")
	void buscarTodosPorPessoa() {
		//ARRANGE
		Long id= 1l;
		
		EnderecoDto enderecoDto1 = new EnderecoDto(UtilTest.ENDERECO5);
		EnderecoDto enderecoDto2 = new EnderecoDto(UtilTest.ENDERECO6);	
		List<EnderecoDto> respostaEsperada = new ArrayList<>(Arrays.asList(enderecoDto1,enderecoDto2));
		
		List<Endereco> responseRepository = new ArrayList<>(Arrays.asList(UtilTest.ENDERECO5,UtilTest.ENDERECO6));
		
		BDDMockito.when(repository.findByPessoaId(id)).thenReturn(responseRepository);
		
		//ACT
		var responseMethod = enderecoService.buscarTodosPorPessoa(id);
					
		//ASSERT
		BDDMockito.then(repository).should().findByPessoaId(idCaptor.capture());
		assertEquals(id, idCaptor.getValue());	
		assertEquals(responseMethod,respostaEsperada);			
	}
	
	@Test
	@DisplayName("Deveria salvar uma lista de enderecos no banco de dados")
	void criarEndereco() {
		//ARRANGE
		List<EnderecoCadastroDto> listaParametro = new ArrayList<>(Arrays.asList(UtilTest.enderecoCadastro1,UtilTest.enderecoCadastro2));
		List<EnderecoDto> listaEsperada = new ArrayList<>(Arrays.asList(
				new EnderecoDto(null,1L,"Rural", "75570-000", "S/N", "Catalao", "GO", false),
				new EnderecoDto(null,1L,"Rural", "75570-000", "S/N", "Catalao", "GO", false)
				));
		
		List<Endereco> listaEntidades = new ArrayList<>(Arrays.asList(new Endereco(UtilTest.enderecoCadastro1),new Endereco(UtilTest.enderecoCadastro2)));
					
		BDDMockito.when(pessoaService.buscarPessoaPorId(UtilTest.enderecoCadastro1.idPessoa())).thenReturn(UtilTest.JOSE);
		BDDMockito.when(pessoaService.buscarPessoaPorId(UtilTest.enderecoCadastro2.idPessoa())).thenReturn(UtilTest.JOSE);
		//ACT
		var responseMethod = enderecoService.criarEndereco(listaParametro);
					
		//ASSERT
		BDDMockito.then(repository).should().saveAll(enderecoListCaptor.capture());
		assertEquals(listaEntidades, enderecoListCaptor.getValue());	
		assertEquals(listaEsperada,responseMethod);			
	}
	
	@Test
	@DisplayName("Deveria salvar uma lista de enderecos no banco de dados")
	void editarEnderecorEndereco() {
		//ARRANGE
		List<EnderecoDto> listaParametro = new ArrayList<>(Arrays.asList(
				new EnderecoDto(1L,1L,"Rural", "75570-000", "S/N", "Catalao", "GO", false),
				new EnderecoDto(2L,1L,"Rural", "75570-000", "S/N", "Catalao", "GO", false)
				));
		
		List<EnderecoDto> listaEsperada = new ArrayList<>(Arrays.asList(
				new EnderecoDto(1L,1L,"Rural", "75570-000", "S/N", "Catalao", "GO", false),
				new EnderecoDto(2L,1L,"Rural", "75570-000", "S/N", "Catalao", "GO", false)
				));
		
		Optional<Endereco> endereco1 = Optional.ofNullable((UtilTest.ENDERECOCOMPESSOA1));	
		Optional<Endereco> endereco2 = Optional.ofNullable((UtilTest.ENDERECOCOMPESSOA2));	
		BDDMockito.when(repository.findById(1L)).thenReturn(endereco1);
		BDDMockito.when(repository.findById(2L)).thenReturn(endereco2);
		//ACT
		var responseMethod = enderecoService.editarEndereco(listaParametro);
					
		//ASSERT 
        verify(repository).save(UtilTest.ENDERECOCOMPESSOA1);
        verify(repository).save(UtilTest.ENDERECOCOMPESSOA1);
		assertEquals(listaEsperada,responseMethod);			
	}

}
