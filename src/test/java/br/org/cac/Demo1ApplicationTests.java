package br.org.cac;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.org.cac.models.Acao;
import br.org.cac.repositories.AcaoRepository;
import br.org.cac.repositories.CampanhaRepository;
import br.org.cac.repositories.DoacaoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {
	
	@Autowired
	DoacaoRepository doacaoRepository;
	
	@Autowired
	AcaoRepository acaoRepository;
	
	@Autowired
	CampanhaRepository campanhaRepository;

	@Test
	public void contextLoads() throws ParseException {
		Acao acao = acaoRepository.findById(4).get();
		acao.setCampanhas(campanhaRepository.findAll());
		acaoRepository.saveAndFlush(acao);
	}

}
