package br.org.cac.controllers.web;

import java.util.Date;
import java.util.List;
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

import br.org.cac.DTO.ItemCampanhaDTO;
import br.org.cac.enums.PorPaginaEnum;
import br.org.cac.models.Acao;
import br.org.cac.models.Campanha;
import br.org.cac.models.CampanhaItem;
import br.org.cac.models.CampanhaItemPK;
import br.org.cac.models.Item;
import br.org.cac.repositories.AcaoRepository;
import br.org.cac.repositories.CampanhaItemRepository;
import br.org.cac.repositories.CampanhaRepository;
import br.org.cac.repositories.ItemRepository;

@Controller
@RequestMapping("/campanhas")
public class CampanhaController {
	@Autowired
	private CampanhaRepository repository;

	@Autowired
	private AcaoRepository acaoRepository;

	@Autowired
	private ItemRepository itemRepository;

	
	
	@Autowired
	private CampanhaItemRepository campanhaItemRepository;
	
	

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
		if (repository.findById(id).isPresent()) {
			Campanha campanha = repository.findById(id).get();
			model.addAttribute("campanha", campanha);
			
			List<CampanhaItem> campanhaItens = campanhaItemRepository.findAllByCampanha(campanha);
			
			model.addAttribute("itensCampanha",campanhaItens);
			
			return "campanhas/show";
			
			
			
		}
		initList();
		return "redirect:/campanhas";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable int id, Model model) {
		if (repository.findById(id).isPresent()) {
			model.addAttribute("campanha", repository.findById(id).get());
			model.addAttribute("acoes", acaoRepository.findAll());
			return "campanhas/edit";
		}
		initList();
		return "redirect:/campanhas/";
	}

	@PostMapping("/{id}/salvar")
	public String salvar(@PathVariable int id, @ModelAttribute @Valid Campanha campanha, Model model) {
		Campanha _campanha = repository.findById(id).get();
		if (_campanha != null && _campanha.getId() == campanha.getId()) {
			try {
				repository.saveAndFlush(campanha);
			} catch (Exception e) {
				System.out.println("Erro ao alterar Campanha: " + e.getMessage());
			}
			return "redirect:/campanhas/" + id + "/show";
		}
		initList();
		return "redirect:/campanhas/";
	}

	@PostMapping("/salvar")
	public String salvarAlteracao(@ModelAttribute @Valid Campanha campanha, Model model) {
		Campanha _campanha = null;
		try {
			_campanha = repository.saveAndFlush(campanha);
		} catch (Exception e) {
			System.out.println("Erro ao salvar Campanha: " + e.getMessage());
		}
		initList();
		return "redirect:/campanhas/" + _campanha.getId() + "/show";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		if(repository.findById(id).isPresent()) {
			repository.deleteById(id);
			initList();			
		}
		return "redirect:/campanhas/";
	}
	
	@GetMapping("/{id}/vincular-itemcampanha")
	public String vincularItemCampanha(@PathVariable int id, Model model) {
		Campanha campanha = repository.findById(id).get();
		model.addAttribute("campanha",campanha);
		model.addAttribute("items",itemRepository.findAll());
		return"campanhas/vincular-itemcampanha";
	}
	
	@PostMapping("/{id}/vincular-itemcampanha")
	public String salvarVincularItemCampanha(@PathVariable int id, @ModelAttribute @Valid Campanha campanha,  Model model) {
    	try {
    		System.out.println("Id Campanha---->"+campanha.getItems().get(0).getNome());
    		Campanha _campanhaSaved = repository.findById(id).get();
    		
    		for(Item item : campanha.getItems()) {
    			
    			CampanhaItem campanhaItem = new CampanhaItem();
    			CampanhaItemPK pk = new CampanhaItemPK();
    			pk.setIdCampanha(campanha.getId());
    			pk.setIdItem(item.getId());
    			//campanhaItem.getId().setIdCampanha(campanha.getId());
    			//campanhaItem.getId().setIdItem(item.getId());
    			campanhaItem.setItem(item);
    			campanhaItem.setCampanha(campanha);
    			campanhaItem.setQuantidade(10);
    			campanhaItem.setId(pk);
    			campanhaItemRepository.saveAndFlush(campanhaItem);
    		}
    		//_campanhaSaved.setItems(campanha.getItems()); // o erro está aqui pois estou setado um objeto Item em um CampanhaItem
		//	campanhaItemRepository.saveAndFlush(_campanhaSaved);
		} catch (Exception e) {
			System.out.println("Erro ao salvar Item Campanha: "+e.getMessage());
		}
		initList();
		return "redirect:/campanhas/"+id+"/show";
	}
	

	/**
	 * Comportamentos
	 */
	@PostConstruct
	public void initList() {
		setSize(5);
		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
		setBusca("");
		setDataFinal(null);
		setDataInicial(null);
		this.campanhaPage = repository.findAll(pageable);
		setCampanhaList(getCampanhaPage().getContent());
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("campanha", new Campanha());
		return "campanhas/create";
	}
	
	
	
	@PostMapping("/proximo")
	public String proximo() {
		if(campanhaPage.hasNext()) {
			campanhaPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), campanhaPage.nextPageable());			
			setCampanhaList(campanhaPage.getContent());
		}		
		return "redirect:/campanhas/";
	}
	
	@PostMapping("/anterior")
	public String anterior() {
		if(campanhaPage.hasPrevious()) {
			campanhaPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), campanhaPage.previousPageable());
			setCampanhaList(campanhaPage.getContent());
		}		
		return "redirect:/campanhas/";
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
		campanhaPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), pageable);
		setCampanhaList(campanhaPage.getContent());		
		
		return "redirect:/campanhas/";
	}
	
	@PostMapping("/resetabusca")
	public String resetabusca() {
		setBusca("");
		setDataInicial(null);
		setDataInicial(null);
		initList();
		return "redirect:/campanhas/";
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

}
