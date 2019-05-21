package br.org.cac.arquitetura;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@SuppressWarnings("rawtypes")
public abstract class AbstractRestController<E, S extends IService, D extends IDTO>  implements IRestController<E, D>{
	@Autowired
	private S service;
	
	@SuppressWarnings("unchecked")
	@GetMapping("")
	@Override
	public ResponseEntity<?> listAll() {
		List<D> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<E> showById(@PathVariable Integer id) {
		if(service.findById(id)==null)
			return ResponseEntity.notFound().build();
		return (ResponseEntity<E>) ResponseEntity.ok().body(service.findById(id));
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("")
	@Override
	public ResponseEntity<?> create(@Valid @RequestBody D object , Errors errors) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();

        if (errors.hasErrors()) {	
            return ResponseEntity.badRequest().body(UtilsValidator.formatarErros(errors));
        }
		
		if(object == null) {
			map.put("success", false);
			map.put("message", "Requisição feita com objeto nulo!");
			return ResponseEntity.badRequest().body(map);
		}
		
		IDTO dto = service.create(object);
		
		if(dto == null) {
			map.put("success", false);
			map.put("message", "Não foi possível executar esta operação!");
			return ResponseEntity.badRequest().body(map);
		}
		
		map.put("success", true);
		map.put("data", dto);
		map.put("message", "Operação realizada com sucesso!");
		
		return ResponseEntity.ok().body(map);
	}
	
	@SuppressWarnings("unchecked")
	@PutMapping("/{id}")
	@Override
	public ResponseEntity<?> update(Integer id, @Valid D object, Errors errors) {
		Map<Object, Object> map = new HashMap<Object, Object>();

        if (errors.hasErrors()) {	
            return ResponseEntity.badRequest().body(UtilsValidator.formatarErros(errors));
        }
		
		if(object == null) {
			map.put("success", false);
			map.put("message", "Requisição feita com objeto nulo!");
			return ResponseEntity.badRequest().body(map);
		}
		
		
		IDTO dto = service.update(id, object);
		
		if(dto == null) {
			map.put("success", false);
			map.put("message", "Não foi possível executar esta operação!");
			return ResponseEntity.badRequest().body(map);
		}
		
		map.put("success", true);
		map.put("data", dto);
		map.put("message", "Operação realizada com sucesso!");
		
		return ResponseEntity.ok().body(map);
	}
	
	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<Map<Object, Object>> delete(@PathVariable Integer id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(service.delete(id)) {
			map.put("success", true);
			return ResponseEntity.ok().body(map);
		}
		
		map.put("success", false);
		return ResponseEntity.ok().body(map);
	}

	public S getService() {
		return service;
	}
	
	
	
}
