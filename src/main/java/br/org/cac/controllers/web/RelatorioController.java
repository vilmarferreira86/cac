package br.org.cac.controllers.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.org.cac.models.Doacao;
import br.org.cac.repositories.DoacaoRepository;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {
	
	@Autowired
	private DoacaoRepository repository;
	
	private Date dataInicial = null;
	private Date dataFinal = null;
	private List<Doacao> doacaoList;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("doacao", getDoacaoList());
		return "relatorios/show";
		
	}

	public List<Doacao> getDoacaoList() {
		return doacaoList;
	}

	public void setDoacaoList(List<Doacao> doacaoList) {
		this.doacaoList = doacaoList;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	

}
