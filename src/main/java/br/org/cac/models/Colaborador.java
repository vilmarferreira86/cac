package br.org.cac.models;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.org.cac.enums.TipoPessoaEnum;


/**
 * The persistent class for the colaborador database table.
 * 
 */
@Entity
 
@NamedQuery(name="Colaborador.findAll", query="SELECT c FROM Colaborador c")
public class Colaborador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@NotNull(message="Campo não pode ser nulo!")
	@NotEmpty(message="Campo não pode estar em branco!")
	private String nome;

	private String bairro;

	private String celular;

	//@Pattern(regexp="^[0-9]{5}\\-[0-9]{3}$", message="Formato do CEP informado é inválido!")
	private String cep;
	
	@Size(max = 250)
    @Column(name = "comoColaborar")
    private String comoColaborar;

	private String complemento;

	@NotEmpty(message="Campo não pode estar em branco!")
	@Size(max = 1)
    @Column(name = "tipoPessoa")
	private String tipoPessoa;
	
	//@CPF(message="O CPF informado é inválido!")
	@Size(max = 18)
    @Column(name = "cpfOuCnpj")
    private String cpfOuCnpj;

	@NotNull(message="Campo E-mail não pode ser nulo!")
	@NotEmpty(message="Campo E-mail não pode estar em branco!")
	@Email(message="E-mail informado é inválido!")
	private String email;

	private String logradouro;

	private String numero;

	private String perfil;

	private String profissao;

	private String senha;

	//bi-directional many-to-many association to Acao
	@ManyToMany
	@JoinTable(
		name="colaborador_acao"
		, joinColumns={
			@JoinColumn(name="colaborador_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="acao_id")
			}
		)
	private List<Acao> acaos;

	@OneToMany(mappedBy="colaborador")
	private List<Doacao> doacaos;

	public Colaborador() {
	}

	public TipoPessoaEnum getTipoPessoa() {
		return TipoPessoaEnum.parse(tipoPessoa);
	}

	public void setTipoPessoa(Optional<TipoPessoaEnum> tipoPessoa) {
		this.tipoPessoa = tipoPessoa.isPresent() ? tipoPessoa.get().getValor() : this.tipoPessoa;
	}




	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComoColaborar() {
		return this.comoColaborar;
	}

	public void setComoColaborar(String comoColaborar) {
		this.comoColaborar = comoColaborar;
	}

	public String getComplemento() {
		return this.complemento;
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
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getProfissao() {
		return this.profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Acao> getAcaos() {
		return this.acaos;
	}

	public void setAcaos(List<Acao> acaos) {
		this.acaos = acaos;
	}

	public List<Doacao> getDoacaos() {
		return this.doacaos;
	}

	public void setDoacaos(List<Doacao> doacaos) {
		this.doacaos = doacaos;
	}

	public Doacao addDoacao(Doacao doacao) {
		getDoacaos().add(doacao);
		doacao.setColaborador(this);

		return doacao;
	}

	public Doacao removeDoacao(Doacao doacao) {
		getDoacaos().remove(doacao);
		doacao.setColaborador(null);

		return doacao;
	}

}