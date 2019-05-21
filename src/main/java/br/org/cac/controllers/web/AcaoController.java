package br.org.cac.controllers.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cac.enums.PorPaginaEnum;
import br.org.cac.models.Acao;
import br.org.cac.repositories.AcaoRepository;
import br.org.cac.repositories.CampanhaRepository;
import br.org.cac.repositories.ColaboradorRepository;;

@Controller
@RequestMapping("/acoes")
public class AcaoController {
	
	@Autowired
	private AcaoRepository repository;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	
	private Integer page = 0;
	private Integer size = 0;
	private List<Acao> acaoList;
	private Page<Acao> acaoPage;
	private String busca = "";
	private List<PorPaginaEnum> porPagina;
	private Date dataInicial = null;
	private Date dataFinal = null;
	
	
	@GetMapping
	public String list(Model model) {		
		model.addAttribute("acoes", getAcaoList());
		model.addAttribute("page", getAcaoPage());
		model.addAttribute("busca", getBusca());
		model.addAttribute("porPagina", getPorPagina());
		model.addAttribute("size", getSize());
		return "acoes/list";
	}
	
	@GetMapping("/{id}/show")
	public String show(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("acao", repository.findById(id).get());				
			return "acoes/show";
		}
		initList();
		return "redirect:/acoes";
	}
	
	@GetMapping("/{id}/editar")
	public String editar(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("acao", repository.findById(id).get());
			model.addAttribute("campanhas", campanhaRepository.findAll());	
			return "acoes/edit";
		}
		initList();
		return "redirect:/acoes/";
	}
	
	@PostMapping("/{id}/salvar")
	public String salvar(
			@PathVariable int id, 
			@ModelAttribute @Valid Acao acao,
			Model model) {
		Acao _acao = repository.findById(id).get();
		if(_acao != null && _acao.getId() == acao.getId()) {
			try {				
				repository.saveAndFlush(acao);
			} catch (Exception e) {
				System.out.println("Erro ao alterar Ação: "+e.getMessage());
			}				
			return "redirect:/acoes/"+id+"/show";
		}
		initList();
		return "redirect:/acoes/";
	}
	
	@PostMapping("/salvar")
	public String salvarAlteracao(
			@ModelAttribute @Valid Acao acao,
			Model model) {
			Acao _acao = null;
        	try {		
        		acao.setCadastro(new Date());
        		_acao = repository.saveAndFlush(acao);
			} catch (Exception e) {
				System.out.println("Erro ao salvar Acão: "+e.getMessage());
			}
			initList();
			return "redirect:/acoes/"+_acao.getId()+"/show";
	}
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("acao", new Acao());		
		model.addAttribute("campanhas", campanhaRepository.findAll());		
		return "acoes/create";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		if(repository.findById(id).isPresent()) {
			repository.deleteById(id);
			initList();			
		}
		return "redirect:/acoes/";
	}
	
	@GetMapping("/{id}/vincular-campanha")
	public String vincularCampanha(@PathVariable int id, Model model) {
		Acao acao = repository.findById(id).get();
		model.addAttribute("acao",acao);
		model.addAttribute("campanhas",campanhaRepository.findAll());
		return"acoes/vincular-campanha";
	}
	
	@GetMapping("/{id}/vincular-colaborador")
	public String vincularColaborador(@PathVariable int id, Model model) {
		Acao acao = repository.findById(id).get();
		model.addAttribute("acao",acao);
		model.addAttribute("colaboradores",colaboradorRepository.findAll());
		
		return"acoes/vincular-colaborador";
	}
	
	@PostMapping("/{id}/vincular-campanha")
	public String salvarVincularCampanha(@PathVariable int id, @ModelAttribute @Valid Acao acao, Model model) {
    	try {		
    		Acao _acaoSaved = repository.findById(id).get();
    		_acaoSaved.setCampanhas(acao.getCampanhas());
			repository.saveAndFlush(_acaoSaved);
		} catch (Exception e) {
			System.out.println("Erro ao salvar Acão: "+e.getMessage());
		}
		initList();
		return "redirect:/acoes/"+id+"/show";
	}
	
	@PostMapping("/{id}/vincular-colaborador")
	public String salvarVincularColaborador(@PathVariable int id, @ModelAttribute @Valid Acao acao, Model model) {
    	try {		
    		Acao _acaoSaved = repository.findById(id).get();
    		_acaoSaved.setColaboradors(acao.getColaboradors());
			repository.saveAndFlush(_acaoSaved);
		} catch (Exception e) {
			System.out.println("Erro ao salvar Acão: "+e.getMessage());
		}
		initList();
		return "redirect:/acoes/"+id+"/show";
	}
	
	/**
	 * Comportamentos
	 */
	@PostConstruct
	public void initList(){
		setSize(5);
		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
		setBusca("");
		setDataFinal(null);
		setDataInicial(null);
		this.acaoPage = repository.findAll(pageable);
		setAcaoList(getAcaoPage().getContent());
	}
	
	@PostMapping("/proximo")
	public String proximo() {
		if(acaoPage.hasNext()) {
			acaoPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), acaoPage.nextPageable());			
			setAcaoList(acaoPage.getContent());
		}		
		return "redirect:/acoes/";
	}
	
	@PostMapping("/anterior")
	public String anterior() {
		if(acaoPage.hasPrevious()) {
			acaoPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), acaoPage.previousPageable());
			setAcaoList(acaoPage.getContent());
		}		
		return "redirect:/acoes/";
	}
	
	@PostMapping("/buscarpor")
	public String buscarpor(
			@RequestParam("busca") Optional<String> busca, 
			@RequestParam("size") Optional<Integer> sizeBusca) {
		
		if(sizeBusca.isPresent()) {
			setSize(sizeBusca.get());
		}
		
		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));				
		setBusca(busca.get());
		acaoPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), pageable);
		setAcaoList(acaoPage.getContent());		
		
		return "redirect:/acoes/";
	}
	
	@PostMapping("/resetabusca")
	public String resetabusca() {
		setBusca("");
		setDataInicial(null);
		setDataInicial(null);
		initList();
		return "redirect:/acoes/";
	}
	
	
		
	/**
	 * GETTERS/SETTERS
	 */
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<Acao> getAcaoList() {
		return acaoList;
	}

	public void setAcaoList(List<Acao> acaoList) {
		this.acaoList = acaoList;
	}

	public Page<Acao> getAcaoPage() {
		return acaoPage;
	}

	public void setAcaoPage(Page<Acao> acaoPage) {
		this.acaoPage = acaoPage;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<PorPaginaEnum> getPorPagina() {
		porPagina = new ArrayList<PorPaginaEnum>();
		porPagina.addAll(Arrays.asList(PorPaginaEnum.values()));
		return porPagina;
	}

	public void setPorPagina(List<PorPaginaEnum> porPagina) {
		this.porPagina = porPagina;
	}
	
	
	
	public BigDecimal parse(final String amount, final Locale locale) throws ParseException {
	    final NumberFormat format = NumberFormat.getNumberInstance(locale);
	    if (format instanceof DecimalFormat) {
	        ((DecimalFormat) format).setParseBigDecimal(true);
	    }
	    return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}
	
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

}
