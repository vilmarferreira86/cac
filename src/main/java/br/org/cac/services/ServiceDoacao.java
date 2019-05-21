package br.org.cac.services;

import org.springframework.stereotype.Service;

import br.org.cac.DTO.DoacaoDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.Doacao;
import br.org.cac.repositories.DoacaoRepository;

@Service
public class ServiceDoacao extends GenericeService<Doacao, DoacaoRepository, DoacaoDTO> {
}
