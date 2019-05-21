package br.org.cac.services;

import org.springframework.stereotype.Service;

import br.org.cac.DTO.ItemCampanhaDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.ItemCampanha;
import br.org.cac.repositories.ItemCampanhaRepository;

@Service
public class ServiceItemCampanha extends GenericeService<ItemCampanha, ItemCampanhaRepository, ItemCampanhaDTO> {

}
