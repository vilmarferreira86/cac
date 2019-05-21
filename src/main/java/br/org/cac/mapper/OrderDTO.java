package br.org.cac.mapper;

import br.org.cac.arquitetura.IDTO;

public class OrderDTO implements IDTO{

	private static final long serialVersionUID = 4481570307768198589L;
	
	public String customerFirstName;
	public String customerLastName;
	public String adressStreet;
	public String adressCity;
	public int adressNumber;
	
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getAdressStreet() {
		return adressStreet;
	}
	public void setAdressStreet(String adressStreet) {
		this.adressStreet = adressStreet;
	}
	public String getAdressCity() {
		return adressCity;
	}
	public void setAdressCity(String adressCity) {
		this.adressCity = adressCity;
	}
	
	public String nomeCompleto() {
		return customerFirstName+" "+customerLastName;
	}
		
	public int getAdressNumber() {
		return adressNumber;
	}
	public void setAdressNumber(int adressNumber) {
		this.adressNumber = adressNumber;
	}
	@Override
	public String toString() {
		return "OrderDTO [\ncustomerFirstName=" + customerFirstName + ", \ncustomerLastName=" + customerLastName
				+ ", \nadressStreet=" + adressStreet + ", \nadressCity=" + adressCity +", \nnumber="+adressNumber+ "\nnomeCompleto="+nomeCompleto()+"\n]";
	}
}
