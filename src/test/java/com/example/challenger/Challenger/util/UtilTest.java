package com.example.challenger.Challenger.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.challenger.Challenger.model.endereco.Endereco;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoCadastroDto;
import com.example.challenger.Challenger.model.endereco.dto.EnderecoDto;
import com.example.challenger.Challenger.model.pessoa.Pessoa;

public class UtilTest {
	
	public static final Pessoa MARIA = new Pessoa(1L, "maria", LocalDate.of(1975, 5, 20), null);
    public static final Pessoa CARLOS = new Pessoa(2L, "carlos", LocalDate.of(1980, 6, 15), null);
    public static final Pessoa JOSE = new Pessoa(1L, "carlos", LocalDate.of(1980, 6, 15), null);

    public static final Endereco ENDERECO1 = new Endereco(1L, "Residencial", "67115810", "06", "Belem", "PA", false, null);
    public static final Endereco ENDERECO2 = new Endereco(2L, "Comercial", "01000-000", "100", "Sao Paulo", "SP", true, null);
    public static final Endereco ENDERECO3 = new Endereco(3L, "Residencial", "40010-020", "250", "Salvador", "BA", false, null);
    public static final Endereco ENDERECO4 = new Endereco(4L, "Rural", "75570-000", "S/N", "Catalao", "GO", false, null);
    public static final Endereco ENDERECO5 = new Endereco(3L, "Residencial", "40010-020", "250", "Salvador", "BA", false, MARIA);
    public static final Endereco ENDERECO6 = new Endereco(4L, "Rural", "75570-000", "S/N", "Catalao", "GO", false, MARIA);
    public static final EnderecoCadastroDto enderecoCadastro1 = new EnderecoCadastroDto(1L, "Rural", "75570-000", "S/N", "Catalao", "GO", false);
    public static final EnderecoCadastroDto enderecoCadastro2 = new EnderecoCadastroDto(1L, "Rural", "75570-000", "S/N", "Catalao", "GO", false);
    
    public static final Endereco ENDERECOCOMPESSOA1 = new Endereco(1L, "Rural", "75570-000", "S/N", "Catalao", "GO", false, MARIA);
    public static final Endereco ENDERECOCOMPESSOA2 = new Endereco(2L, "Rural", "75570-000", "S/N", "Catalao", "GO", false, MARIA);

    public static final EnderecoDto enderecoDto1 = new EnderecoDto(1L,UtilTest.enderecoCadastro1.idPessoa(),UtilTest.enderecoCadastro1.logradouro(),
			UtilTest.enderecoCadastro1.cep(),UtilTest.enderecoCadastro1.numero(),UtilTest.enderecoCadastro1.cidade(),
			UtilTest.enderecoCadastro1.estado(),UtilTest.enderecoCadastro1.principal());
	
    public static final EnderecoDto enderecoDto2 = new EnderecoDto(1L,UtilTest.enderecoCadastro2.idPessoa(),UtilTest.enderecoCadastro2.logradouro(),
			UtilTest.enderecoCadastro2.cep(),UtilTest.enderecoCadastro2.numero(),UtilTest.enderecoCadastro2.cidade(),
			UtilTest.enderecoCadastro2.estado(),UtilTest.enderecoCadastro2.principal());
    
    
    static {
        List<Endereco> enderecosMaria = new ArrayList<>(Arrays.asList(ENDERECO1, ENDERECO2));
        List<Endereco> enderecosCarlos = new ArrayList<>(Arrays.asList(ENDERECO3, ENDERECO4));
        List<Endereco> enderecosJose = new ArrayList<>(Arrays.asList(ENDERECO6, ENDERECO6));

        MARIA.setEndereco(enderecosMaria);
        CARLOS.setEndereco(enderecosCarlos);
        JOSE.setEndereco(enderecosJose);
    }
    
    private UtilTest() {
        
    }
}
