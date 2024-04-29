package com.example.challenger.Challenger.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.challenger.Challenger.model.endereco.dto.EnderecoCadastroDto;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;
import com.example.challenger.Challenger.service.EnderecoService;
import com.example.challenger.Challenger.service.exceptions.ResourceNotFound;
import com.example.challenger.Challenger.util.UtilTest;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@SpringBootTest
class EnderecoControllerTest {
	
	@MockBean
	private EnderecoService service;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<List<EnderecoDto>> listEnderecoDtoJackson;  
	
	@Autowired
	private JacksonTester<List<EnderecoCadastroDto>> listCadastroEnderecoDtoJackson;
	
	@Autowired
	private JacksonTester<EnderecoDto> enderecoDtoJackson;

	@Test
	@DisplayName("Deveria retornar um objeto do tipo List<EnderecoDto> e status 200ok")
	void listarTodosCenario01() throws Exception {	
		//ARRANGE										
		EnderecoDto enderecoDto1 = new EnderecoDto(UtilTest.ENDERECO5);
		EnderecoDto enderecoDto2 = new EnderecoDto(UtilTest.ENDERECO6);	
		List<EnderecoDto> response = new ArrayList<>(Arrays.asList(enderecoDto1,enderecoDto2));
		
		var jsonEsperado = this.listEnderecoDtoJackson.write(response).getJson();		
		BDDMockito.when(service.buscarTodos()).thenReturn(response);	
		//ACT
		var responseEndPoint = mvc
		.perform(get("/endereco/listar-todos"))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).buscarTodos();			 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);		
	}
	
	@Test
	@DisplayName("Deveria retornar uma objeto do tipo List<EnderecoDto> e o status 200ok")
	void listarPessoa() throws Exception {	
		//ARRANGE
		Long id = 1l;	
		EnderecoDto enderecoDto1 = new EnderecoDto(UtilTest.ENDERECO5);
		EnderecoDto enderecoDto2 = new EnderecoDto(UtilTest.ENDERECO6);	
		List<EnderecoDto> response = new ArrayList<>(Arrays.asList(enderecoDto1,enderecoDto2));
		
		var jsonEsperado = this.listEnderecoDtoJackson.write(response).getJson();		
		BDDMockito.when(service.buscarTodosPorPessoa(id)).thenReturn(response);	
		
		//ACT
		var responseEndPoint = mvc
		.perform(get("/endereco/"+id))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).buscarTodosPorPessoa(id);		 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);
	}
	
	
	@Test
	@DisplayName("Deveria retornar uma objeto do tipo EnderecoDto e o status 200ok")
	void listarEnderecoEspecifico() throws Exception {	
		//ARRANGE
		Long id = 1l;	
		EnderecoDto response = new EnderecoDto(UtilTest.ENDERECO5);
		
		var jsonEsperado = this.enderecoDtoJackson.write(response).getJson();		
		BDDMockito.when(service.buscarEnderecoPorId(id)).thenReturn(UtilTest.ENDERECO5);	
		
		//ACT
		var responseEndPoint = mvc
		.perform(get("/endereco/especifico/"+id))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).buscarEnderecoPorId(id);		 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);
	}
	
	
	@Test
	@DisplayName("Deveria retornar uma objeto do tipo EnderecoDto e o status 200ok")
	void listarEnderecoEspecificoCenario02() throws Exception {	
		//ARRANGE
		Long id = 1l;		
		BDDMockito.when(service.buscarEnderecoPorId(id)).thenThrow(new ResourceNotFound(id));	
		
		//ACT
		 mvc
		.perform(get("/endereco/especifico/"+id))
	    .andExpect(status().isNotFound()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).buscarEnderecoPorId(id);		 
	}
	
	@Test
	@DisplayName("Deveria retornar uma objeto do tipo List<EnderecoDto> e o status 201ok")
	void cadastrarEndereco() throws Exception {	
		//ARRANGE
		List<EnderecoCadastroDto> listRequest = new ArrayList<>(Arrays.asList(UtilTest.enderecoCadastro1,UtilTest.enderecoCadastro2));
				
		EnderecoDto enderecoDto1 = new EnderecoDto(1l,UtilTest.enderecoCadastro1.idPessoa(),UtilTest.enderecoCadastro1.logradouro(),
				UtilTest.enderecoCadastro1.cep(),UtilTest.enderecoCadastro1.numero(),UtilTest.enderecoCadastro1.cidade(),
				UtilTest.enderecoCadastro1.estado(),UtilTest.enderecoCadastro1.principal());
		
		EnderecoDto enderecoDto2 = new EnderecoDto(2l,UtilTest.enderecoCadastro2.idPessoa(),UtilTest.enderecoCadastro2.logradouro(),
				UtilTest.enderecoCadastro2.cep(),UtilTest.enderecoCadastro2.numero(),UtilTest.enderecoCadastro2.cidade(),
				UtilTest.enderecoCadastro2.estado(),UtilTest.enderecoCadastro2.principal());
		
		List<EnderecoDto> response = new ArrayList<>(Arrays.asList(enderecoDto1,enderecoDto2));
		
		var jsonEsperado = this.listEnderecoDtoJackson.write(response).getJson();		
		BDDMockito.when(service.criarEndereco(listRequest)).thenReturn(response);	
		
		//ACT
		var responseEndPoint = mvc
		.perform(post("/endereco/cadastrar").contentType(MediaType.APPLICATION_JSON)
		.content(this.listCadastroEnderecoDtoJackson.write(listRequest).getJson()))
	    .andExpect(status().isCreated()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).criarEndereco(listRequest);		 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);
	}
	
	
	@Test
	@DisplayName("Deveria retornar uma objeto do tipo List<EnderecoDto> e o status 200ok")
	void atualizarEndereco() throws Exception {	
		//ARRANGE				
	
		List<EnderecoDto> requisicao = new ArrayList<>(Arrays.asList(UtilTest.enderecoDto1,UtilTest.enderecoDto2));
		
		var jsonEsperado = this.listEnderecoDtoJackson.write(requisicao).getJson();		
		BDDMockito.when(service.editarEndereco(requisicao)).thenReturn(requisicao);	
		
		//ACT
		var responseEndPoint = mvc
		.perform(put("/endereco/atualizar").contentType(MediaType.APPLICATION_JSON)
		.content(this.listEnderecoDtoJackson.write(requisicao).getJson()))
	    .andExpect(status().isOk()) //assert
	    .andReturn().getResponse();
		
		//ASSERT				
		 verify(service, times(1)).editarEndereco(requisicao);		 
		 assertThat(responseEndPoint.getContentAsString()).isEqualTo(jsonEsperado);
	}
	
}




















