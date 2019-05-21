package br.org.cac.services;

import org.springframework.stereotype.Service;

import br.org.cac.DTO.CampanhaDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.Campanha;
import br.org.cac.repositories.CampanhaRepository;

@Service
public class ServiceCampanha extends GenericeService<Campanha, CampanhaRepository, CampanhaDTO> {


}
