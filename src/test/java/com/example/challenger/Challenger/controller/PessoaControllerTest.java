package com.example.challenger.Challenger.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.challenger.Challenger.model.pessoa.Pessoa;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaCadastroDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaEditarDadosPessoaisDto;
import com.example.challenger.Challenger.service.PessoaService;
import com.example.challenger.Challenger.service.exceptions.ResourceNotFound;
import com.example.challenger.Challenger.util.UtilTest;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@SpringBootTest
class PessoaControllerTest {
	
	@MockBean
	private PessoaService service;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<List<PessoaDto>> listaPessoaDto;
	
	@Autowired
	private JacksonTester<PessoaDto> pessoaDto;
	
	@Autowired
	private JacksonTester<List<Pessoa>> pessoaListResponse;
	
	@Autowired
	private JacksonTester<List<PessoaCadastroDto>> pessoaCadastroDtoList;
	
	@Autowired
	private JacksonTester<List<PessoaEditarDadosPessoaisDto>> pessoasEditadasList;
	
	@Test
	@DisplayName("Deveria retornar uma lista do tipo List<PessoaDto> e o status 200ok")
	void listarTodos() throws Exception {
	
		//ARRANGE										
		PessoaDto pessoaDto1 = new PessoaDto(UtilTest.MARIA.getId(),UtilTest.MARIA.getNomeCompleto(),UtilTest.MARIA.getDataNascimento(),UtilTest.MARIA.getEndereco()); 
		PessoaDto pessoaDto2 = new PessoaDto(UtilTest.CARLOS.getId(),UtilTest.CARLOS.getNomeCompleto(),UtilTest.CARLOS.getDataNascimento(),UtilTest.CARLOS.getEndereco()); 
		List<PessoaDto> response  = new ArrayList<>(Arrays.asList(pessoaDto1,pessoaDto2));
		
		var jsonEsperado = this.listaPessoaDto.write(response).getJson();		
		BDDMockito.when(service.listarTodos()).thenReturn(response);	
		//ACT
		var responseEndPoint = mvc
		.perform(get("/pessoa/listar-todos"))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).listarTodos();			 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);
	}
	
	@Test
	@DisplayName("Deveria retornar uma objeto do tipo PessoaDto e o status 200ok")
	void listarPessoa() throws Exception {	
		//ARRANGE
		Long id = 1l;	
		PessoaDto response  = new PessoaDto(UtilTest.MARIA.getId(),UtilTest.MARIA.getNomeCompleto(),UtilTest.MARIA.getDataNascimento(), UtilTest.MARIA.getEndereco());
		
		var jsonEsperado = this.pessoaDto.write(response).getJson();		
		BDDMockito.when(service.buscarPessoaPorId(id)).thenReturn(UtilTest.MARIA);	
		
		//ACT
		var responseEndPoint = mvc
		.perform(get("/pessoa/"+id))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).buscarPessoaPorId(id);			 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);
	
	}
	
	@Test
	@DisplayName("Deveria retornar uma exceção ao informar um id inexistente")
	void listarPessoaCenario02() throws Exception {		
		//ARRANGE
		Long id = 1l;		
		BDDMockito.when(service.buscarPessoaPorId(id)).thenThrow(new ResourceNotFound(id));
		
		//ACT
		 mvc
		 .perform(get("/pessoa/"+id))
	     .andExpect(status().isNotFound()) //assert
	     .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).buscarPessoaPorId(id);			 
	}
	
	@Test
	@DisplayName("Deveria retornar uma lista do tipo List<Pessoa> e o status 201created")
	void cadastroPessoa() throws Exception {
		//ARRANGE
		List<Pessoa> response = new ArrayList<>(Arrays.asList(UtilTest.MARIA,UtilTest.CARLOS));
										
		PessoaCadastroDto pessoaCadastroDto1 = new PessoaCadastroDto("maria",LocalDate.of(1975, 5, 20),
				new ArrayList<>(Arrays.asList(UtilTest.ENDERECO1,UtilTest.ENDERECO2)));
		
		PessoaCadastroDto pessoaCadastroDto2 = new PessoaCadastroDto("carlos",LocalDate.of(1980, 6, 15),
				new ArrayList<>(Arrays.asList(UtilTest.ENDERECO3,UtilTest.ENDERECO4)));
		
		List<PessoaCadastroDto> request = new ArrayList<>(Arrays.asList(pessoaCadastroDto1,pessoaCadastroDto2));
						
		var jsonEsperado = this.pessoaListResponse.write(response).getJson();		
		BDDMockito.when(service.cadastrarPessoas(request)).thenReturn(response);
		
		//ACT
		var responseEndPoint = mvc
		.perform(post("/pessoa/cadastro")
		.contentType(MediaType.APPLICATION_JSON)
		.content(this.pessoaCadastroDtoList.write(request).getJson()))
	    .andExpect(status().isCreated()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).cadastrarPessoas(request);			 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);	
	}
	
	@Test
	@DisplayName("Deveria retornar uma lista do tipo List<PessoaEditarDadosPessoaisDto> e o status 200ok")
	void editarPessoas() throws Exception {	
		//ASSERT	
		PessoaDto pessoaDto1 = new PessoaDto(UtilTest.MARIA.getId(),UtilTest.MARIA.getNomeCompleto(),
				UtilTest.MARIA.getDataNascimento(),UtilTest.MARIA.getEndereco()); 
		
		PessoaDto pessoaDto2 = new PessoaDto(UtilTest.CARLOS.getId(),UtilTest.CARLOS.getNomeCompleto(),
				UtilTest.CARLOS.getDataNascimento(),UtilTest.CARLOS.getEndereco()); 
		
		List<PessoaDto> jsonListRequest = new ArrayList<>(Arrays.asList(pessoaDto2, pessoaDto1));	

		PessoaEditarDadosPessoaisDto mariaResponse = new PessoaEditarDadosPessoaisDto(UtilTest.MARIA);
		PessoaEditarDadosPessoaisDto carlosResponse = new PessoaEditarDadosPessoaisDto(UtilTest.CARLOS);
		List<PessoaEditarDadosPessoaisDto> responseList = new ArrayList<>(Arrays.asList(mariaResponse,carlosResponse));	
		var jsonEsperado = this.pessoasEditadasList.write(responseList).getJson();
			
		BDDMockito.when(service.editarPessoas(jsonListRequest)).thenReturn(responseList);
		
		//ACT
		var responseEndPoint = mvc
		.perform(put("/pessoa/editar")
		.contentType(MediaType.APPLICATION_JSON)
		.content(this.listaPessoaDto.write(jsonListRequest).getJson()))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).editarPessoas(jsonListRequest);			 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);	
	}
}


