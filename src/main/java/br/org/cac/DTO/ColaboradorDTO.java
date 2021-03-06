package br.org.cac.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.cac.arquitetura.IDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;


public class ColaboradorDTO implements IDTO {

	private static final long serialVersionUID = -7018017070949760754L;

	

	private int id;

	private String nome;

	private String bairro;

	private String celular;

	//@Pattern(regexp="^[0-9]{5}\\-[0-9]{3}$", message="Formato do CEP informado é inválido!")
	private String cep;
	
    private String comoColaborar;

	private String complemento;
	
	@CPF(message="O CPF informado é inválido!")
    private String cpfOuCnpj;
    
	@Email(message="E-mail informado é inválido!")
	private String email;

	private String logradouro;

	private String numero;

	private String perfil;

	private String profissao;


	public ColaboradorDTO() {
	}


	public ColaboradorDTO(int id, String nome, String bairro, String celular, String cep, String comoColaborar,
			String complemento, String cpfOuCnpj, String email, String logradouro, String numero, String perfil,
			String profissao) {
		super();
		this.id = id;
		this.nome = nome;
		this.bairro = bairro;
		this.celular = celular;
		this.cep = cep;
		this.comoColaborar = comoColaborar;
		this.complemento = complemento;
		this.cpfOuCnpj = cpfOuCnpj;
		this.email = email;
		this.logradouro = logradouro;
		this.numero = numero;
		this.perfil = perfil;
		this.profissao = profissao;
	}


	public ColaboradorDTO(String nome, String bairro, String celular, String cep, String comoColaborar,
			String complemento, String cpfOuCnpj, String email, String logradouro, String numero, String perfil,
			String profissao) {
		super();
		this.nome = nome;
		this.bairro = bairro;
		this.celular = celular;
		this.cep = cep;
		this.comoColaborar = comoColaborar;
		this.complemento = complemento;
		this.cpfOuCnpj = cpfOuCnpj;
		this.email = email;
		this.logradouro = logradouro;
		this.numero = numero;
		this.perfil = perfil;
		this.profissao = profissao;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCelular() {
		return celular;
	}


	public void setCelular(String celular) {
		this.celular = celular;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getComoColaborar() {
		return comoColaborar;
	}


	public void setComoColaborar(String comoColaborar) {
		this.comoColaborar = comoColaborar;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}


	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getPerfil() {
		return perfil;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	public String getProfissao() {
		return profissao;
	}


	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}


	@Override
	public String toString() {
		return "ColaboradorDTO [id=" + id + ", nome=" + nome + ", bairro=" + bairro + ", celular=" + celular + ", cep="
				+ cep + ", comoColaborar=" + comoColaborar + ", complemento=" + complemento + ", cpfOuCnpj=" + cpfOuCnpj
				+ ", email=" + email + ", logradouro=" + logradouro + ", numero=" + numero + ", perfil=" + perfil
				+ ", profissao=" + profissao + "]";
	}

	
}
