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

import com.example.challenger.Challenger.model.pessoa.Pessoa;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaCadastroDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaEditarDadosPessoaisDto;
import com.example.challenger.Challenger.service.PessoaService;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

	@Autowired
	private PessoaService service;
	
	
	@GetMapping("listar-todos")
	public ResponseEntity<List<PessoaDto>> listarTodos(){
		var response = service.listarTodos();
			return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PessoaDto> listarPessoa(@PathVariable Long id){
		Pessoa pessoa = service.buscarPessoaPorId(id);
		PessoaDto response = new PessoaDto(pessoa.getId(), pessoa.getNomeCompleto(),pessoa.getDataNascimento(), pessoa.getEndereco());
			return ResponseEntity.ok(response);
	}
	
	@PostMapping("cadastro")
	public ResponseEntity<List<Pessoa>> cadastroPessoas(@RequestBody List<PessoaCadastroDto> dto){
		var response = service.cadastrarPessoas(dto);
		 	return ResponseEntity.created(URI.create("/pessoa/listar-todos")).body(response);
	}
	
	
	@PutMapping("editar")
	public ResponseEntity<List<PessoaEditarDadosPessoaisDto>> editarPessoas(@RequestBody List<PessoaDto> dto){
		var response = service.editar(dto);
			return ResponseEntity.ok(response);
	}
	

	
	
	
}
