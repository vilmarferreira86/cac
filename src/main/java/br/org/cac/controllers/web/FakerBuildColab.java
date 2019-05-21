package br.org.cac.controllers.web;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

import br.org.cac.DTO.ColaboradorDTO;
import br.org.cac.services.ServiceColaborador;

//@Configuration
public class FakerBuildColab {
	
	@Autowired
	private ServiceColaborador service;
	
	@PostConstruct
	public void makeColaboradores() {
		Faker faker = new Faker(new Locale("pt-BR"));
		for(int i = 0; i <= 500; i++ ) {
			
			ColaboradorDTO colab = new ColaboradorDTO(
					faker.name().firstName()+" "+faker.name().lastName(), 
					faker.address().firstName(), 
					"("+faker.number().digits(2)+") 9"+faker.number().digits(4)+"-"+faker.number().digits(4), 
					faker.number().digits(5)+"-"+faker.number().digits(3), 
					faker.lorem().sentence(3), 
					faker.address().streetName()+", "+faker.address().streetName(), 
					faker.number().digits(11), 
					faker.internet().emailAddress(), 
					faker.address().streetAddress(), 
					faker.address().streetAddressNumber(), 
					faker.artist().name(), 
					faker.job().title());
			
			service.create(colab);
		}
		
		//System.out.println(colab.toString()); 
	}
}
