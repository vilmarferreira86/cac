package br.org.cac.controllers.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cac.DTO.ColaboradorDTO;
import br.org.cac.arquitetura.AbstractRestController;
import br.org.cac.models.Colaborador;
import br.org.cac.repositories.ColaboradorRepository;
import br.org.cac.services.ServiceColaborador;

@RestController
@RequestMapping("/api/v1/colaboradores")
public class ColaboradorRest extends AbstractRestController<Colaborador, ServiceColaborador, ColaboradorDTO> {
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@GetMapping("/to-select")
	public ResponseEntity<?> listAllToSelect(@RequestParam String search) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		List<Colaborador> list = colaboradorRepository.findByNomeContainingAllIgnoreCase(search);
		
		List<Map<Object, Object>> selectObject = new ArrayList<Map<Object,Object>>();
		for(Colaborador c : list) {
			Map<Object, Object> object = new HashMap<Object, Object>();
			object.put("id", c.getId());
			object.put("text", c.getNome());
			selectObject.add(object);
		}
		
		map.put("results", selectObject);
		return ResponseEntity.ok().body(map);
	}

}
