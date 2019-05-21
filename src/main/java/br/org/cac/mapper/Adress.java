package br.org.cac.mapper;

public class Adress {
	
	public String street;
	public String city;
	private int number;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Adress [street=" + street + ", city=" + city + ", number=" + number + "]";
	}
	
	

}
