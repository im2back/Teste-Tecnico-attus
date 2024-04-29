package com.example.challenger.Challenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.challenger.Challenger.model.pessoa.Pessoa;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaCadastroDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaDto;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaEditarDadosPessoaisDto;
import com.example.challenger.Challenger.repository.PessoaRepository;
import com.example.challenger.Challenger.service.exceptions.ResourceNotFound;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository repository;
	
	@Transactional(readOnly = true)
	public Pessoa buscarPessoaPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFound(id));
	}
	
	@Transactional(readOnly = true)
	public List<PessoaDto> listarTodos() {	
		var lista = repository.findAll();	
		 List<PessoaDto> response = lista.stream()
		            .map(element -> new PessoaDto(element.getId(), element.getNomeCompleto(), element.getDataNascimento(), element.getEndereco()))
		            .collect(Collectors.toList());	
		 
		return response ;		
	}
		
	@Transactional
	public List<Pessoa> cadastrarPessoas(List<PessoaCadastroDto> listaDto) {
		
		 List<Pessoa> listaEntidades = listaDto.stream()
		            .map(dto -> {
		                Pessoa pessoa = new Pessoa(dto.nomeCompleto(),dto.dataNascimento(),null);		            
		                dto.endereco().forEach( e -> e.setPessoa(pessoa));		                
		                pessoa.setEndereco(dto.endereco());                
		                return pessoa;}).collect(Collectors.toList());
		 
		 				repository.saveAll(listaEntidades);	
		 				return listaEntidades;
	}
	
	@Transactional
	public List<PessoaEditarDadosPessoaisDto> editarPessoas(List<PessoaDto> listaDto) {
		
		List<PessoaEditarDadosPessoaisDto> response = new ArrayList<>();
		
		for (PessoaDto p :listaDto) {
			Pessoa pessoa = buscarPessoaPorId(p.id());	
			pessoa.update(p);			
			repository.save(pessoa);
			response.add(new PessoaEditarDadosPessoaisDto(pessoa));
		}				
		return response;
	}
	
	
	
	
	
}
