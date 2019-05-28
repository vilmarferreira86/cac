package br.org.cac.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Proxy(lazy=true) 
public class Doacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date cadastro;

	private BigDecimal total;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="colaborador")
	private Colaborador colaborador;

	@OneToMany(mappedBy="doacao")
	private List<ItemDoacao> itemDoacaos;
	
	@Transient
	private List<Campanha> campanhas;
	
	@Transient
	private List<Item> items;

	public Doacao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCadastro() {
		return this.cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public BigDecimal getTotal() {
		return this.total;
	}
	
	public String getTotalFormatado() {
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(this.total);
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Colaborador getColaborador() {
		return this.colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public List<ItemDoacao> getItemDoacaos() {
		return this.itemDoacaos;
	}

	public void setItemDoacaos(List<ItemDoacao> itemDoacaos) {
		this.itemDoacaos = itemDoacaos;
	}

	public ItemDoacao addItemDoacao(ItemDoacao itemDoacao) {
		getItemDoacaos().add(itemDoacao);
		itemDoacao.setDoacao(this);

		return itemDoacao;
	}

	public ItemDoacao removeItem(ItemDoacao itemDoacao) {
		getItemDoacaos().remove(itemDoacao);
		itemDoacao.setDoacao(null);

		return itemDoacao;
	}

	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	
	
	

}