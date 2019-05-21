package br.org.cac.controllers.web;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.org.cac.enums.PorPaginaEnum;
import br.org.cac.models.Colaborador;
import br.org.cac.repositories.ColaboradorRepository;


@Controller
@RequestMapping("/colaboradores")
public class ColaboradorController {
	@Autowired
	private ColaboradorRepository repository;
	
	private Colaborador VO = new Colaborador();
	
	private Integer page = 0;
	private Integer size = 0;
	private List<Colaborador> colabList;
	private Page<Colaborador> colabPage;
	private String buscaNome = "";
	private List<PorPaginaEnum> porPagina;
	

	public Colaborador getVO() {
		return VO;
	}

	public void setVO(Colaborador vO) {
		VO = vO;
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

	public List<Colaborador> getColabList() {
		return colabList;
	}

	public void setColabList(List<Colaborador> colabList) {
		this.colabList = colabList;
	}
	
	public void teste() {
		System.out.println("page: "+page+" size: "+size);
	}	

	public Page<Colaborador> getColabPage() {
		return colabPage;
	}	

	public void setColabPage(Page<Colaborador> colabPage) {
		this.colabPage = colabPage;
	}

	public String getBuscaNome() {
		return buscaNome;
	}

	public void setBuscaNome(String buscaNome) {
		this.buscaNome = buscaNome;
	}
	
	public List<PorPaginaEnum> getPorPagina(){
		porPagina = new ArrayList<PorPaginaEnum>();
		porPagina.addAll(Arrays.asList(PorPaginaEnum.values()));
		return porPagina;
	}

	/**
	 * Comportamentos
	 */
	
	public Integer getPaginaAtual() {
		return getColabPage().getNumber() + 1;
	}
	
	@PostMapping("/proximo")
	public String proximo() {
		if(colabPage.hasNext()) {
			colabPage = repository.findByNomeContainingOrEmailContainingOrCelularContaining(buscaNome, buscaNome, buscaNome, colabPage.nextPageable());;
			setColabList(colabPage.getContent());
		}
		
		return "redirect:/colaboradores";
	}
	
	@PostMapping("/anterior")
	public String anterior() {
		if(colabPage.hasPrevious()) {
			colabPage = repository.findByNomeContainingOrEmailContainingOrCelularContaining(buscaNome, buscaNome, buscaNome, colabPage.previousPageable());
			setColabList(colabPage.getContent());
		}
		
		return "redirect:/colaboradores";
	}
	
	@PostMapping("/buscarpor")
	public String buscarpor(@RequestParam("busca") Optional<String> busca, 
			@RequestParam("size") Optional<Integer> sizeBusca) {
		
		if(busca.isPresent()) {
			if(sizeBusca.isPresent()) {
				setSize(sizeBusca.get());
			}
				
			setBuscaNome(busca.get());
			Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
			colabPage = repository.findByNomeContainingOrEmailContainingOrCelularContainingOrProfissaoContaining(buscaNome, buscaNome, buscaNome, buscaNome, pageable);
			setColabList(colabPage.getContent());
		}
		
		return "redirect:/colaboradores";
	}
	
	@PostMapping("/resetabusca")
	public String resetabusca() {
		setBuscaNome("");
		initListColaborador();
		return "redirect:/colaboradores";
	}
		
	@PostConstruct
	public void initListColaborador(){
		setSize(5);
		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
		colabPage = repository.findByNomeContainingOrEmailContainingOrCelularContaining(buscaNome, buscaNome, buscaNome, pageable);
		setBuscaNome("");
		setColabList(colabPage.getContent());
	}
	
	@GetMapping
	public String listColaborador(Model model){
		model.addAttribute("colaboradores", getColabList());
		model.addAttribute("page", getColabPage());
		model.addAttribute("busca", getBuscaNome());
		model.addAttribute("porPagina", getPorPagina());
		model.addAttribute("size", getSize());
		return "colaboradores/list";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		if(repository.findById(id).isPresent()) {
			repository.deleteById(id);
			initListColaborador();			
		}
		return "redirect:/colaboradores";
	}
	
	@GetMapping("/{id}/show")
	public String show(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("colaborador", repository.findById(id).get());				
			return "colaboradores/show";
		}
		initListColaborador();
		return "redirect:/colaboradores";
	}//@ModelAttribute @Valid Privilegio privilegio
	
	@GetMapping("/{id}/editar")
	public String editar(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("colaborador", repository.findById(id).get());				
			return "colaboradores/edit";
		}
		initListColaborador();
		return "redirect:/colaboradores";
	}
	
	@PostMapping("/{id}/salvar")
	public String salvar(@PathVariable int id, Model model, @ModelAttribute @Valid Colaborador colaborador) {
		if(repository.findById(id).isPresent()) {
			try {
				repository.saveAndFlush(colaborador);
			} catch (Exception e) {
				System.out.println("Erro ao alterar Colaborador: "+e.getMessage());
			}				
			return "redirect:/colaboradores/"+id+"/show";
		}
		initListColaborador();
		return "redirect:/colaboradores/";
	}
	
	@PostMapping("/salvar")
	public String salvarAlteracao(@ModelAttribute @Valid Colaborador colaborador, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
            return "colaboradores/create";
        }else {
			try {
				repository.saveAndFlush(colaborador);
			} catch (Exception e) {
				System.out.println("Erro ao salvar Colaborador: "+e.getMessage());
			}
			initListColaborador();
			return "redirect:/colaboradores/";
        }
	}
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("colaborador", new Colaborador());		
		return "colaboradores/create";
	}
	
}
