package br.org.cac.controllers.web;

import java.util.Date;
import java.util.List;

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

import br.org.cac.enums.PorPaginaEnum;
import br.org.cac.models.Acao;
import br.org.cac.models.Campanha;
import br.org.cac.repositories.AcaoRepository;
import br.org.cac.repositories.CampanhaRepository;
import br.org.cac.repositories.ItemCampanhaRepository;
import br.org.cac.repositories.ItemDoacaoRepository;

@Controller
@RequestMapping("/campanhas")
public class CampanhaController {
	@Autowired
	private CampanhaRepository repository;
	
	@Autowired
	private AcaoRepository acaoRepository;
	
	@Autowired
	private ItemCampanhaRepository itemCampanhaRepository;
	
	@Autowired
	private ItemDoacaoRepository itemDoacaoRepository;
	
	private Integer page = 0;
	private Integer size = 0;
	private List<Campanha> campanhaList;
	private Page<Campanha> campanhaPage;
	private String busca = "";
	private List<PorPaginaEnum> porPagina;
	private Date dataInicial = null;
	private Date dataFinal = null;
	
	
	@GetMapping
	public String list(Model model) {		
		model.addAttribute("campanhas", getCampanhaList());
		model.addAttribute("page", getCampanhaPage());
		model.addAttribute("busca", getBusca());
		model.addAttribute("porPagina", getPorPagina());
		model.addAttribute("size", getSize());
		return "campanhas/list";
	}
	
	@GetMapping("/{id}/show")
	public String show(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("campanha", repository.findById(id).get());				
			return "campanhas/show";
		}
		initList();
		return "redirect:/campanhas";
	}
	
	@GetMapping("/{id}/editar")
	public String editar(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("campanha", repository.findById(id).get());
			model.addAttribute("acoes", acaoRepository.findAll());	
			return "campanhas/edit";
		}
		initList();
		return "redirect:/campanhas/";
	}
	
	@PostMapping("/{id}/salvar")
	public String salvar(
			@PathVariable int id, 
			@ModelAttribute @Valid Campanha campanha,
			Model model) {
		Campanha _campanha = repository.findById(id).get();
		if(_campanha != null && _campanha.getId() == campanha.getId()) {
			try {				
				repository.saveAndFlush(campanha);
			} catch (Exception e) {
				System.out.println("Erro ao alterar Campanha: "+e.getMessage());
			}				
			return "redirect:/campanhas/"+id+"/show";
		}
		initList();
		return "redirect:/campanhas/";
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
		this.campanhaPage = repository.findAll(pageable);
		setCampanhaList(getCampanhaPage().getContent());
	}

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



	public List<Campanha> getCampanhaList() {
		return campanhaList;
	}



	public void setCampanhaList(List<Campanha> campanhaList) {
		this.campanhaList = campanhaList;
	}



	public Page<Campanha> getCampanhaPage() {
		return campanhaPage;
	}



	public void setCampanhaPage(Page<Campanha> campanhaPage) {
		this.campanhaPage = campanhaPage;
	}



	public String getBusca() {
		return busca;
	}



	public void setBusca(String busca) {
		this.busca = busca;
	}



	public List<PorPaginaEnum> getPorPagina() {
		return porPagina;
	}



	public void setPorPagina(List<PorPaginaEnum> porPagina) {
		this.porPagina = porPagina;
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



	public CampanhaRepository getRepository() {
		return repository;
	}

	public void setRepository(CampanhaRepository repository) {
		this.repository = repository;
	}

	public AcaoRepository getAcaoRepository() {
		return acaoRepository;
	}

	public void setAcaoRepository(AcaoRepository acaoRepository) {
		this.acaoRepository = acaoRepository;
	}

	public ItemCampanhaRepository getItemCampanhaRepository() {
		return itemCampanhaRepository;
	}

	public void setItemCampanhaRepository(ItemCampanhaRepository itemCampanhaRepository) {
		this.itemCampanhaRepository = itemCampanhaRepository;
	}

	public ItemDoacaoRepository getItemDoacaoRepository() {
		return itemDoacaoRepository;
	}

	public void setItemDoacaoRepository(ItemDoacaoRepository itemDoacaoRepository) {
		this.itemDoacaoRepository = itemDoacaoRepository;
	}
	
	
	
	
	

}
