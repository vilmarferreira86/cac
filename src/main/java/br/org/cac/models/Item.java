package br.org.cac.models;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Source;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the item_campanha database table.
 * 
 */
@Entity
@Table(name="item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 255)
	private String nome;
	
	@Column (length = 255)
	private String descricao;
	
	@Transient
//	@OneToMany(mappedBy = "item")
	private List<Campanha> campanhas;
	

	//bi-directional many-to-one association to ItemDoacao
	@OneToMany(mappedBy="items")
	private List<ItemDoacao> itemDoacaos;
	
	

	public Item() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	


	

	

	

	

	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

	public List<ItemDoacao> getItemDoacaos() {
		return this.itemDoacaos;
	}

	public void setItemDoacaos(List<ItemDoacao> itemDoacaos) {
		this.itemDoacaos = itemDoacaos;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/*public ItemDoacao addItemDoacao(ItemDoacao itemDoacao) {
		getItemDoacaos().add(itemDoacao);
		itemDoacao.setItemCampanha(this);

		return itemDoacao;
	}

	public ItemDoacao removeItemDoacao(ItemDoacao itemDoacao) {
		getItemDoacaos().remove(itemDoacao);
		itemDoacao.setItemCampanha(null);

		return itemDoacao;
	}*/


	

}