package br.org.cac.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.org.cac.DTO.ColaboradorDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.Colaborador;
import br.org.cac.repositories.ColaboradorRepository;

@Service
public class ServiceColaborador extends GenericeService<Colaborador, ColaboradorRepository, ColaboradorDTO> {
	
	public Page<Colaborador> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}
	
}
