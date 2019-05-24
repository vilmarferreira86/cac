package br.org.cac.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CampanhaItemPK implements Serializable {

	/**
	 * 
	 */
	@Column(name="item_id")
	private int idItem;
	@Column(name="campanha_id")
	private int idCampanha;
	
	public CampanhaItemPK() {
		
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public int getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(int idCampanha) {
		this.idCampanha = idCampanha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCampanha;
		result = prime * result + idItem;
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
		CampanhaItemPK other = (CampanhaItemPK) obj;
		if (idCampanha != other.idCampanha)
			return false;
		if (idItem != other.idItem)
			return false;
		return true;
	}

	
	
}
