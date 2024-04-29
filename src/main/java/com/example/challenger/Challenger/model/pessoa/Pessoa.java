package com.example.challenger.Challenger.model.pessoa;

import java.time.LocalDate;
import java.util.List;

import com.example.challenger.Challenger.model.endereco.Endereco;
import com.example.challenger.Challenger.model.pessoa.dto.PessoaDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_pessoa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeCompleto;

	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "pessoa",cascade = CascadeType.ALL)
	private List<Endereco> endereco;

	public Pessoa(String nomeCompleto, LocalDate dataNascimento, List<Endereco> endereco) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
	}

	public void update(PessoaDto p) {
		
		    if (p.nomeCompleto() != null) {
		        this.setNomeCompleto(p.nomeCompleto());
		    }
		    if (p.dataNascimento() != null) {
		        this.setDataNascimento(p.dataNascimento());
		    }
				
	}
	

	
}
