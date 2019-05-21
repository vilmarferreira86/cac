package br.org.cac.enums;

import org.springframework.stereotype.Service;

@Service
public class ResourceColaborador extends AbstracResource{

	@Override
	String getResource() {
		return EnumResources.COLABORADOR.getDescricao();
	}	
}
