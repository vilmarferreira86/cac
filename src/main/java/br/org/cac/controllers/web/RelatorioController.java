package br.org.cac.controllers.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cac.models.Doacao;
import br.org.cac.repositories.DoacaoRepository;
import br.org.cac.repositories.ItemDoacaoRepository;
import br.org.cac.services.RelatorioService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

	@Autowired
	private DoacaoRepository repository;

	@Autowired
	private ItemDoacaoRepository itemDoacaorepository;

	@Autowired
	private RelatorioService relService;


	private Date dataInicial = null;
	private Date dataFinal = null;
	private List<Doacao> doacaoList;

	@GetMapping
	public String list(Model model){
		model.addAttribute("doacao", getDoacaoList());
		model.addAttribute("dataInicial", dataInicial);
		model.addAttribute("dataFinal", dataFinal);
	
		return "relatorios/show";

	}
	

	@GetMapping("/relatorio")
	public void doRelatorio(HttpServletResponse response,
			@RequestParam("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date _dataInicial,
			@RequestParam("dataFinal") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date _dataFinal) throws IOException{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=doacoes.pdf");
		
		relService.doRelatorio(response.getOutputStream(),_dataInicial, _dataFinal);
	}



	

	
	@PostConstruct
	public void initList() {

		setDataFinal(null);
		setDataInicial(null);
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
