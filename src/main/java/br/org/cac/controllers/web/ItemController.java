package br.org.cac.controllers.web;

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

import br.org.cac.enums.PorPaginaEnum;
import br.org.cac.models.Campanha;
import br.org.cac.models.CampanhaItem;
import br.org.cac.models.Item;
import br.org.cac.repositories.ItemRepository;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemRepository repository;
	
	private Integer page = 0;
	private Integer size = 0;
	private List<Item> itemList;
	private Page<Item> itemPage;
	private String busca = "";
	private List<PorPaginaEnum> porPagina;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("items", getItemList());
		model.addAttribute("page", getItemPage());
		model.addAttribute("busca", getBusca());
		model.addAttribute("porPagina", getPorPagina());
		model.addAttribute("size", getSize());
		return "item/list";
	}
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("item", new Item());
		return "item/create";
	}
	
	@GetMapping("/{id}/show")
	public String show(@PathVariable int id, Model model) {
		if (repository.findById(id).isPresent()) {
			Item item = repository.findById(id).get();
			model.addAttribute("item", item);
		
			return "item/show";
			
			
			
		}
		initList();
		return "redirect:/item";
	}
	
	@GetMapping("/{id}/editar")
	public String editar(@PathVariable int id, Model model) {
		if (repository.findById(id).isPresent()) {
			model.addAttribute("item", repository.findById(id).get());
			return "item/edit";
		}
		initList();
		return "redirect:/item/";
	}
	
	@PostConstruct
	public void initList() {
		setSize(5);
		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
		setBusca("");
		this.itemPage = repository.findAll(pageable);
		setItemList(getItemPage().getContent());
	}
	
	@PostMapping("/{id}/salvar")
	public String salvar(@PathVariable int id, @ModelAttribute @Valid Item item, Model model) {
		Item _item = repository.findById(id).get();
		if (_item != null && _item.getId() == item.getId()) {
			try {
				repository.saveAndFlush(item);
			} catch (Exception e) {
				System.out.println("Erro ao alterar Item: " + e.getMessage());
			}
			return "redirect:/item/" + id + "/show";
		}
		initList();
		return "redirect:/item/";
	}
	
	@PostMapping("/salvar")
	public String salvarAlteracao(@ModelAttribute @Valid Item item, Model model) {
		Item _item = null;
		try {
			_item = repository.saveAndFlush(item);
		} catch (Exception e) {
			System.out.println("Erro ao salvar Item: " + e.getMessage());
		}
		initList();
		return "redirect:/item/" + _item.getId() + "/show";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		if(repository.findById(id).isPresent()) {
			repository.deleteById(id);
			initList();			
		}
		return "redirect:/campanhas/";
	}
	
	@PostMapping("/proximo")
	public String proximo() {
		if(itemPage.hasNext()) {
			itemPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), itemPage.nextPageable());			
			setItemList(itemPage.getContent());
		}		
		return "redirect:/item/";
	}
	
	@PostMapping("/anterior")
	public String anterior() {
		if(itemPage.hasPrevious()) {
			itemPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), itemPage.previousPageable());
			setItemList(itemPage.getContent());
		}		
		return "redirect:/item/";
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
		itemPage = repository.findByNomeOrDescricaoContaining(getBusca(), getBusca(), pageable);
		setItemList(itemPage.getContent());		
		
		return "redirect:/item/";
	}
	
	@PostMapping("/resetabusca")
	public String resetabusca() {
		setBusca("");
		
		initList();
		return "redirect:/item/";
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

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Page<Item> getItemPage() {
		return itemPage;
	}

	public void setItemPage(Page<Item> itemPage) {
		this.itemPage = itemPage;
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
	
	
	

}
