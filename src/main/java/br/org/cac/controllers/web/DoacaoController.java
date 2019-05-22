package br.org.cac.controllers.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import br.org.cac.models.Colaborador;
import br.org.cac.models.Doacao;
import br.org.cac.repositories.CampanhaRepository;
import br.org.cac.repositories.ColaboradorRepository;
import br.org.cac.repositories.DoacaoRepository;;

@Controller
@RequestMapping("/doacoes")
public class DoacaoController {
	
	@Autowired
	private DoacaoRepository repository;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@Autowired
	private CampanhaRepository campanhaRepository;

	
	
	private Integer page = 0;
	private Integer size = 0;
	private List<Doacao> doacaoList;
	private Page<Doacao> doacaoPage;
	private String busca = "";
	private List<PorPaginaEnum> porPagina;
	private Date dataInicial = null;
	private Date dataFinal = null;
	
	
	@GetMapping
	public String list(Model model) {
		
		model.addAttribute("doacoes", getDoacaoList());
		model.addAttribute("page", getDoacaoPage());
		model.addAttribute("busca", getBusca());
		model.addAttribute("dataInicial", getDataInicial()!=null ? new SimpleDateFormat("yyyy-MM-dd").format(getDataInicial()) : "");
		model.addAttribute("dataFinal", getDataFinal()!=null ? new SimpleDateFormat("yyyy-MM-dd").format(getDataFinal()) : "");
		model.addAttribute("porPagina", getPorPagina());
		model.addAttribute("size", getSize());
		return "doacoes/list";
	}
	
	@GetMapping("/{id}/show")
	public String show(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("doacao", repository.findById(id).get());				
			return "doacoes/show";
		}
		initList();
		return "redirect:/doacoes";
	}
	
	@GetMapping("/{id}/editar")
	public String editar(@PathVariable int id, Model model) {
		if(repository.findById(id).isPresent()) {
			model.addAttribute("doacao", repository.findById(id).get());				
			return "doacoes/edit";
		}
		initList();
		return "redirect:/doacoes/";
	}
	
	@PostMapping("/{id}/salvar")
	public String salvar(
			@PathVariable int id, 
			@RequestParam("cadastro") Optional<String> _cadastro,
			@RequestParam("total") Optional<String> _total,
			Model model) {
		Doacao doacao = repository.findById(id).get();
		if(doacao != null) {
			try {
				doacao.setCadastro(new SimpleDateFormat("yyyy-MM-dd").parse(_cadastro.get()));
				doacao.setTotal(parse(_total.get().replace(".", ""), Locale.FRANCE));
				repository.saveAndFlush(doacao);
			} catch (Exception e) {
				System.out.println("Erro ao alterar Doação: "+e.getMessage());
			}				
			return "redirect:/doacoes/"+id+"/show";
		}
		initList();
		return "redirect:/doacoes/";
	}
	
	@PostMapping("/salvar")
	public String salvarAlteracao(
			@RequestParam("colaborador") Optional<String> _colaborador,
			@RequestParam("cadastro") Optional<String> _cadastro,
			@RequestParam("total") Optional<String> _total,
			Model model) {
		
		if (!_colaborador.isPresent() || !_cadastro.isPresent() || !_total.isPresent()) {
            return "doacoes/create";
        }else {
        	Doacao doacao = new Doacao();
        	try {
        		doacao.setColaborador(colaboradorRepository.getOne(Integer.parseInt(_colaborador.get())));
				doacao.setCadastro(new SimpleDateFormat("yyyy-MM-dd").parse(_cadastro.get()));
				doacao.setTotal(parse(_total.get().replace(".", ""), Locale.FRANCE));			
				repository.saveAndFlush(doacao);
			} catch (Exception e) {
				System.out.println("Erro ao salvar Doacão: "+e.getMessage());
			}
			initList();
			return "redirect:/doacoes/";
        	
        }
	}
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("colaboradores", colaboradorRepository.findAll());		
		return "doacoes/create";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		if(repository.findById(id).isPresent()) {
			repository.deleteById(id);
			initList();			
		}
		return "redirect:/doacoes/";
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
		this.doacaoPage = repository.findAll(pageable);
		setDoacaoList(getDoacaoPage().getContent());
	}
	
	@PostMapping("/proximo")
	public String proximo() {
		if(doacaoPage.hasNext()) {
			if(!getBusca().equals("") && getDataInicial()!=null && getDataFinal()!=null) {
				doacaoPage = repository.findByColaboradorNomeContainingAndCadastroBetween(getBusca(), getDataInicial(), getDataFinal(), doacaoPage.nextPageable());
			}
			if(getBusca().equals("") && getDataInicial()!=null && getDataFinal()!=null) {
				doacaoPage = repository.findByCadastroBetween(getDataInicial(), getDataFinal(), doacaoPage.nextPageable());
			}
			if(!getBusca().equals("") && getDataInicial()==null && getDataFinal()==null) {
				doacaoPage = repository.findByColaboradorNomeContaining(getBusca(), doacaoPage.nextPageable());
			}
			setDoacaoList(doacaoPage.getContent());
		}		
		return "redirect:/doacoes/";
	}
	
	@PostMapping("/anterior")
	public String anterior() {
		if(doacaoPage.hasPrevious()) {
			if(!getBusca().equals("") && getDataInicial()!=null && getDataFinal()!=null) {
				doacaoPage = repository.findByColaboradorNomeContainingAndCadastroBetween(getBusca(), getDataInicial(), getDataFinal(), doacaoPage.previousPageable());
			}
			if(getBusca().equals("") && getDataInicial()!=null && getDataFinal()!=null) {
				doacaoPage = repository.findByCadastroBetween(getDataInicial(), getDataFinal(), doacaoPage.previousPageable());
			}
			if(!getBusca().equals("") && getDataInicial()==null && getDataFinal()==null) {
				doacaoPage = repository.findByColaboradorNomeContaining(getBusca(), doacaoPage.previousPageable());
			}
			setDoacaoList(doacaoPage.getContent());
		}		
		return "redirect:/doacoes/";
	}
	
	@PostMapping("/buscarpor")
	public String buscarpor(
			@RequestParam("busca") Optional<String> busca, 
			@RequestParam("dataInicial") Optional<String> _dataInicial,
			@RequestParam("dataFinal") Optional<String> _dataFinal,
			@RequestParam("size") Optional<Integer> sizeBusca) {
		
		if(sizeBusca.isPresent()) {
			setSize(sizeBusca.get());
		}
		
		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
		
		if(!busca.get().equals("") && _dataInicial.get().equals("") && _dataFinal.get().equals("")) {				
			setBusca(busca.get());
			doacaoPage = repository.findByColaboradorNomeContaining(getBusca(), pageable);
			setDoacaoList(doacaoPage.getContent());
		}
		
		if(!busca.get().equals("") && !_dataInicial.get().equals("") && !_dataFinal.get().equals("")) {
			try {
				setDataInicial(new SimpleDateFormat("yyyy-MM-dd").parse(_dataInicial.get()));
				setDataFinal(new SimpleDateFormat("yyyy-MM-dd").parse(_dataFinal.get()));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
				
			setBusca(busca.get());			
			doacaoPage = repository.findByColaboradorNomeContainingAndCadastroBetween(getBusca(), getDataInicial(), getDataFinal(), pageable);
			setDoacaoList(doacaoPage.getContent());
		}
		
		if(busca.get().equals("") && !_dataInicial.get().equals("") && !_dataFinal.get().equals("")) {
			try {
				setDataInicial(new SimpleDateFormat("yyyy-MM-dd").parse(_dataInicial.get()));
				setDataFinal(new SimpleDateFormat("yyyy-MM-dd").parse(_dataFinal.get()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			doacaoPage = repository.findByCadastroBetween(getDataInicial(), getDataFinal(), pageable);
			setDoacaoList(doacaoPage.getContent());
		}
		
		return "redirect:/doacoes/";
	}
	
	@PostMapping("/resetabusca")
	public String resetabusca() {
		setBusca("");
		setDataInicial(null);
		setDataInicial(null);
		initList();
		return "redirect:/doacoes/";
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

	public List<Doacao> getDoacaoList() {
		return doacaoList;
	}

	public void setDoacaoList(List<Doacao> doacaoList) {
		this.doacaoList = doacaoList;
	}

	public Page<Doacao> getDoacaoPage() {
		return doacaoPage;
	}

	public void setDoacaoPage(Page<Doacao> doacaoPage) {
		this.doacaoPage = doacaoPage;
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
