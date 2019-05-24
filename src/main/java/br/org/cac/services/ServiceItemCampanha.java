package br.org.cac.services;

import org.springframework.stereotype.Service;

import br.org.cac.DTO.ItemCampanhaDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.Item;
import br.org.cac.repositories.ItemRepository;

@Service
public class ServiceItemCampanha extends GenericeService<Item, ItemRepository, ItemCampanhaDTO> {

}
