package br.org.cac.arquitetura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class UtilsValidator {
	
	private static Map<Object, Object> map = new HashMap<Object, Object>();
	
	public static Map<Object, Object> formatarErros(Errors errors){
		
		List<String> listErrors = new ArrayList<>();
		
		for (ObjectError erro : errors.getAllErrors()) {
			listErrors.add(erro.getDefaultMessage());
		}
		map.put("errors", listErrors);
		return map;
	}

}
