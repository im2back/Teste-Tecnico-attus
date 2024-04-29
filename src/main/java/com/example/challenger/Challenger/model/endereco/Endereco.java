package com.example.challenger.Challenger.model.endereco;

import com.example.challenger.Challenger.model.endereco.dto.EnderecoCadastroDto;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;
import com.example.challenger.Challenger.model.pessoa.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_endereco")
@EqualsAndHashCode(of="id")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String logradouro;
	private String cep;
	private String numero;
	private String cidade;
	private String estado;
	private boolean principal;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	@JsonIgnore
	private Pessoa pessoa;

	public Endereco(String logradouro, String cep, String numero, String cidade, String estado, boolean principal,
			Pessoa pessoa) {
		super();
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.principal = principal;
		this.pessoa = pessoa;
	}
	
	public Endereco(EnderecoCadastroDto dto) {
		super();
		this.logradouro = dto.logradouro();
		this.cep = dto.cep();
		this.numero = dto.numero();
		this.cidade = dto.cidade();
		this.estado = dto.estado();
		this.principal = dto.principal();
		this.pessoa = null;
	}
	
		
	public void updateEndereco(EnderecoDto dto) {
	    if (dto.cep() != null) {
	        this.setCep(dto.cep());
	    }
	    if (dto.cidade() != null) {
	        this.setCidade(dto.cidade());
	    }
	    if (dto.estado() != null) {
	        this.setEstado(dto.estado());
	    }
	    if (dto.logradouro() != null) {
	        this.setLogradouro(dto.logradouro());
	    }
	    if (dto.numero() != null) {
	        this.setNumero(dto.numero());
	    }
	    this.setPrincipal(dto.principal()); 
	}

	

}
