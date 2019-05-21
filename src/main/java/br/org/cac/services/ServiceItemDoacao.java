package br.org.cac.services;

import org.springframework.stereotype.Service;

import br.org.cac.DTO.ItemDoacaoDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.ItemDoacao;
import br.org.cac.repositories.ItemDoacaoRepository;

@Service
public class ServiceItemDoacao extends GenericeService<ItemDoacao, ItemDoacaoRepository, ItemDoacaoDTO> {

}
