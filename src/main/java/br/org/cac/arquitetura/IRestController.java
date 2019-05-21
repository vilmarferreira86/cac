package br.org.cac.arquitetura;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface IRestController<E, D extends IDTO> {
		
	public ResponseEntity<?> listAll();
//	public ResponseEntity<List<E>> listAll();
	
	public ResponseEntity<E> showById(@PathVariable(value = "id") Integer id);
	
	public ResponseEntity<?> create(@Valid @RequestBody D object, Errors errors);
	
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer id, @Valid @RequestBody D object, Errors errors);
	
	public ResponseEntity<Map<Object, Object>> delete(@PathVariable(value = "id") Integer id);

}
