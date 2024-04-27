package com.example.challenger.Challenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.challenger.Challenger.model.endereco.Endereco;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoCadastroDto;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;
import com.example.challenger.Challenger.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	@Autowired
	private PessoaService pessoaService;

	@Transactional(readOnly = true)
	public Endereco buscarEnderecoPorId(Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Endereco n'ao encontrado para o ID: " + id));
	}

	@Transactional(readOnly = true)
	public List<EnderecoDto> buscarTodos() {
		var lista = repository.findAll();
		List<EnderecoDto> response = lista.stream().map(EnderecoDto::new).collect(Collectors.toList());
		return response;
	}

	@Transactional(readOnly = true)
	public List<EnderecoDto> buscarTodosPorPessoa(Long id) {
		var lista = repository.findByPessoaId(id);
		List<EnderecoDto> response = lista.stream().map(EnderecoDto::new).collect(Collectors.toList());
		return response;
	}

	@Transactional
	public List<Endereco> editarEndereco(List<EnderecoDto> listaDto) {

		List<Endereco> response = new ArrayList<>();

		for (EnderecoDto e : listaDto) {
			Endereco endereco = buscarEnderecoPorId(e.idEndereco());
			endereco.updateEndereco(e);
			repository.save(endereco);
			response.add(endereco);
		}
		return response;
	}

	@Transactional
	public List<EnderecoDto> criarEndereco(List<EnderecoCadastroDto> dto) {

		List<Endereco> listaEntidades = dto.stream().map(p -> {
			Endereco endereco = new Endereco(p);
			endereco.setPessoa(pessoaService.buscarPessoaPorId(p.idPessoa()));
			return endereco;
		}).collect(Collectors.toList());
		repository.saveAll(listaEntidades);

		List<EnderecoDto> responseList = listaEntidades.stream().map(EnderecoDto::new).collect(Collectors.toList());
		return responseList;

	}

}
