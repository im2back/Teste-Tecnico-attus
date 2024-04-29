package com.example.challenger.Challenger.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenger.Challenger.model.endereco.dto.EnderecoCadastroDto;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;
import com.example.challenger.Challenger.service.EnderecoService;

@RequestMapping("endereco")
@RestController
public class EnderecoController {

	@Autowired
	private EnderecoService service;
	
	@GetMapping("listar-todos")
	public ResponseEntity<List<EnderecoDto>> listarTodos(){
		var response = service.buscarTodos();
			return	ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<EnderecoDto>> listarTodosPorPessoa(@PathVariable Long id){
		var response = service.buscarTodosPorPessoa(id);
			return	ResponseEntity.ok(response);
	}
	
	@GetMapping("especifico/{id}")
	public ResponseEntity<EnderecoDto> listarEnderecoEspecifico(@PathVariable Long id){
		var response = service.buscarEnderecoPorId(id);
			return	ResponseEntity.ok(new EnderecoDto(response));
	}
	
	@PostMapping("cadastrar")
	public ResponseEntity<List<EnderecoDto>> cadastrarEndereco(@RequestBody List<EnderecoCadastroDto> dto){
		var response = service.criarEndereco(dto);
		return ResponseEntity.created(URI.create("/endereco/listar-todos")).body(response);
	}
	
	@PutMapping("atualizar")
	public ResponseEntity<List<EnderecoDto>> atualizarEndereco(@RequestBody List<EnderecoDto> dto){
		var response = service.editarEndereco(dto);
		return ResponseEntity.ok(response);
	}
	
	
	
}
