package br.org.cac.models;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the campanha database table.
 * 
 */
@Entity
@NamedQuery(name="Campanha.findAll", query="SELECT c FROM Campanha c")
public class Campanha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private BigDecimal arrecadado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cadastro;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;

	private String descricao;

	private BigDecimal doacaoMinima;

	private byte habilitada;

	private BigDecimal meta;

	private String nome;

	//bi-directional many-to-many association to Acao
	@ManyToMany
	@JoinTable(
		name="campanha_acao"
		, joinColumns={
			@JoinColumn(name="campanha_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="acao_id")
			}
		)
	private List<Acao> acaos;

	//bi-directional many-to-one association to ItemCampanha
	@OneToMany(mappedBy="campanha")
	private List<ItemCampanha> itemCampanhas;

	//bi-directional many-to-one association to ItemDoacao
	@OneToMany(mappedBy="campanha")
	private List<ItemDoacao> itemDoacaos;

	public Campanha() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getArrecadado() {
		return this.arrecadado;
	}

	public void setArrecadado(BigDecimal arrecadado) {
		this.arrecadado = arrecadado;
	}

	public Date getCadastro() {
		return this.cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getDoacaoMinima() {
		return this.doacaoMinima;
	}

	public void setDoacaoMinima(BigDecimal doacaoMinima) {
		this.doacaoMinima = doacaoMinima;
	}

	public byte getHabilitada() {
		return this.habilitada;
	}

	public void setHabilitada(byte habilitada) {
		this.habilitada = habilitada;
	}

	public BigDecimal getMeta() {
		return this.meta;
	}

	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Acao> getAcaos() {
		return this.acaos;
	}

	public void setAcaos(List<Acao> acaos) {
		this.acaos = acaos;
	}

	public List<ItemCampanha> getItemCampanhas() {
		return this.itemCampanhas;
	}

	public void setItemCampanhas(List<ItemCampanha> itemCampanhas) {
		this.itemCampanhas = itemCampanhas;
	}

	public ItemCampanha addItemCampanha(ItemCampanha itemCampanha) {
		getItemCampanhas().add(itemCampanha);
		itemCampanha.setCampanha(this);

		return itemCampanha;
	}

	public ItemCampanha removeItemCampanha(ItemCampanha itemCampanha) {
		getItemCampanhas().remove(itemCampanha);
		itemCampanha.setCampanha(null);

		return itemCampanha;
	}

	public List<ItemDoacao> getItemDoacaos() {
		return this.itemDoacaos;
	}

	public void setItemDoacaos(List<ItemDoacao> itemDoacaos) {
		this.itemDoacaos = itemDoacaos;
	}

	public ItemDoacao addItemDoacao(ItemDoacao itemDoacao) {
		getItemDoacaos().add(itemDoacao);
		itemDoacao.setCampanha(this);

		return itemDoacao;
	}

	public ItemDoacao removeItemDoacao(ItemDoacao itemDoacao) {
		getItemDoacaos().remove(itemDoacao);
		itemDoacao.setCampanha(null);

		return itemDoacao;
	}

}