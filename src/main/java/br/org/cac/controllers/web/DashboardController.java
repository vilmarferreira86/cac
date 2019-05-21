package br.org.cac.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping("/dashboard")
	public String dashboard(){
//		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
//		colabPage = repository.findByNomeContainingOrEmailContainingOrCelularContaining(buscaNome, buscaNome, buscaNome, pageable);
//		
//		setColabList(colabPage.getContent());
		return "dashboard";
	}

	@GetMapping("/")
	public String index(){
//		Pageable pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "id"));
//		colabPage = repository.findByNomeContainingOrEmailContainingOrCelularContaining(buscaNome, buscaNome, buscaNome, pageable);
//		
//		setColabList(colabPage.getContent());
		return "dashboard";
	}
}
