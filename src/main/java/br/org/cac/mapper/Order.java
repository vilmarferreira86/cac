package br.org.cac.mapper;

public class Order {
	
	public Customer customer;
	public Adress adress;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Adress getAdress() {
		return adress;
	}
	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	@Override
	public String toString() {
		return "Order [customer=" + customer + ", adress=" + adress +  "]";
	}
	
	

}
