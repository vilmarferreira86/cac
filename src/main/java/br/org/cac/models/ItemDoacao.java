package br.org.cac.models;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the item_doacao database table.
 * 
 */
@Entity
@Table(name="item_doacao")
@NamedQuery(name="ItemDoacao.findAll", query="SELECT i FROM ItemDoacao i")
public class ItemDoacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String descricao;

	private BigDecimal valor;
	
	@Column
	private int quantidade;

	//bi-directional many-to-one association to Campanha
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="campanha")
	private Campanha campanha;

	//bi-directional many-to-one association to Doacao
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="doacao")
	private Doacao doacao;

	//bi-directional many-to-one association to ItemCampanha
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_campanha")
	private ItemCampanha itemCampanha;

	public ItemDoacao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Campanha getCampanha() {
		return this.campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Doacao getDoacao() {
		return this.doacao;
	}

	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
	}

	public ItemCampanha getItemCampanha() {
		return this.itemCampanha;
	}

	public void setItemCampanha(ItemCampanha itemCampanha) {
		this.itemCampanha = itemCampanha;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	

}