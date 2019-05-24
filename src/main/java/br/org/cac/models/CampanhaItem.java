package br.org.cac.models;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.ibm.icu.math.BigDecimal;

@Entity
public class CampanhaItem{
	
	
	@EmbeddedId
	private CampanhaItemPK id;
	
	@ManyToOne
	@MapsId("item_id")
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne
	@MapsId("campanha_id")
	@JoinColumn(name = "campanha_id")
	private Campanha campanha;
	
	@Column(nullable = false)
	private int quantidade;
	
	private BigDecimal valor;
	
	
	
	public CampanhaItemPK getId() {
		return id;
	}
	public void setId(CampanhaItemPK id) {
		this.id = id;
	}
	
	
	public CampanhaItem() {
	}
	
	
	
	public CampanhaItem(CampanhaItemPK id, Item item, Campanha campanha, int quantidade, BigDecimal valor) {
		super();
		this.id = id;
		this.item = item;
		this.campanha = campanha;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Campanha getCampanha() {
		return campanha;
	}
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampanhaItem other = (CampanhaItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	

}
